package org.iranshahi.zoochallenge.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.business.mapper.AnimalMapper;
import org.iranshahi.zoochallenge.business.service.AnimalPlacementService;
import org.iranshahi.zoochallenge.business.service.AnimalService;
import org.iranshahi.zoochallenge.data.model.Animal;
import org.iranshahi.zoochallenge.data.repository.AnimalRepository;
import org.iranshahi.zoochallenge.data.repository.RoomRepository;
import org.iranshahi.zoochallenge.exceptions.AnimalNotFoundException;
import org.iranshahi.zoochallenge.exceptions.RoomNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService, AnimalPlacementService {
    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final RoomRepository roomRepository;


    @Override
    public AnimalDto create(AnimalDto animalDto) {
        var instance = animalMapper.toEntity(animalDto);
        instance = animalRepository.save(instance);
        return animalMapper.toDto(instance);
    }

    private Animal findById(String animalId) {
        return animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));
    }

    @Override
    public AnimalDto update(String animalId, AnimalDto animalDto) {
        var existing = findById(animalId);
        existing.setTitle(animalDto.title());
        existing.setLocated(animalDto.located());
        existing = animalRepository.save(existing);
        return animalMapper.toDto(existing);
    }

    @Override
    public AnimalDto get(String animalId) {
        return animalMapper.toDto(findById(animalId));
    }

    @Override
    public void delete(String animalId) {
        try {
            animalRepository.deleteById(animalId);
        } catch (Exception ex) {
            //todo reza find the correct exception throws by hibernate
            throw new AnimalNotFoundException(animalId);
        }
    }


    @Override
    public AnimalDto place(String animalId, String roomId) {
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
        var animal = findById(animalId);
        animal.setRoomId(room.getId());
        animal = animalRepository.save(animal);
        return animalMapper.toDto(animal);
    }

    @Override
    public AnimalDto remove(String animalId) {
        var animal = findById(animalId);
        animal.setRoomId(null);
        animal = animalRepository.save(animal);
        return animalMapper.toDto(animal);
    }
}
