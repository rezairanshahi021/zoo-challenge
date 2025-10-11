package org.iranshahi.zoochallenge.business.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.business.mapper.AnimalMapper;
import org.iranshahi.zoochallenge.business.service.PlacementService;
import org.iranshahi.zoochallenge.data.model.Animal;
import org.iranshahi.zoochallenge.data.model.Room;
import org.iranshahi.zoochallenge.data.repository.AnimalRepository;
import org.iranshahi.zoochallenge.data.repository.RoomRepository;
import org.iranshahi.zoochallenge.exceptions.*;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlacementServiceImpl implements PlacementService {

    private final RoomRepository roomRepository;
    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;


    private static final int MAX_RETRIES = 4;


    private void checkRules(Room room, Animal animal) {
        if (room.getAllowedCategory() != null && room.getAllowedCategory() != animal.getCategory()) {
            throw new CategoryMismatchException(room.getAllowedCategory().name(), animal.getCategory().name());
        }

        double remaining = room.getCapacity() - room.getUsedVolume();
        if (animal.getVolume() > remaining + 1e-9) {
            throw new RoomOutOfSpaceException(room.getId());
        }

    }

    private void updateRoom(Room room, Animal animal) {
        room.setUsedVolume(room.getUsedVolume() + animal.getVolume());
        room.getAnimalIds().add(animal.getId());
        if (room.getAllowedCategory() == null) {
            room.setAllowedCategory(animal.getCategory());
        }
        roomRepository.save(room);

    }

    private void updateAnimal(Room room, Animal animal) {
        animal.setRoomId(room.getId());
        animalRepository.save(animal);
    }

    @Override
    @Transactional
    public AnimalDto place(String animalId, String roomId) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                Animal animal = animalRepository.findById(animalId)
                        .orElseThrow(() -> new AnimalNotFoundException(animalId));
                Room room = roomRepository.findById(roomId)
                        .orElseThrow(() -> new RoomNotFoundException(roomId));

                if (roomId.equals(animal.getRoomId())) {
                    return animalMapper.toDto(animal);
                }

                checkRules(room, animal);
                updateRoom(room, animal);
                updateAnimal(room, animal);

                log.debug("Animal [{}] placed in room [{}] (attempt {})", animalId, roomId, attempt);
                return animalMapper.toDto(animal);

            } catch (OptimisticLockingFailureException e) {
                log.warn("Optimistic lock conflict placing animal [{}] in room [{}], retry {}/{}",
                        animalId, roomId, attempt, MAX_RETRIES);
                sleepBackoff(attempt);

                if (attempt == MAX_RETRIES) {
                    throw new ConcurrencyException();
                }
            }
        }
        throw new ConcurrencyException();
    }

    @Override
    @Transactional
    public AnimalDto remove(String animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));

        if (animal.getRoomId() == null) {
            throw new AnimalNotPlacedException(animalId);
        }
        Room room = roomRepository.findById(animal.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException(animal.getRoomId()));
        adjustRoom(room, animal);
        removeAnimalRoom(animal);

        log.debug("🐾 Animal [{}] removed from room [{}]", animalId, room.getId());
        return animalMapper.toDto(animal);
    }

    private void removeAnimalRoom(Animal animal) {
        animal.setRoomId(null);
        animalRepository.save(animal);
    }

    private void adjustRoom(Room room, Animal animal) {
        room.getAnimalIds().remove(animal.getId());
        room.setUsedVolume(Math.max(0, room.getUsedVolume() - animal.getVolume()));

        if (room.getAnimalIds().isEmpty()) {
            room.setAllowedCategory(null);
        }
        roomRepository.save(room);
    }

    private void sleepBackoff(int attempt) {
        try {
            Thread.sleep(100L * attempt);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
