package org.iranshahi.zoochallenge.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.iranshahi.zoochallenge.business.dto.RoomDto;
import org.iranshahi.zoochallenge.business.mapper.RoomMapper;
import org.iranshahi.zoochallenge.business.service.RoomManagementService;
import org.iranshahi.zoochallenge.data.model.Room;
import org.iranshahi.zoochallenge.data.repository.RoomRepository;
import org.iranshahi.zoochallenge.exceptions.DuplicateRoomTitleException;
import org.iranshahi.zoochallenge.exceptions.RoomNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Reza Iranshahi
 * @version 1.0.0
 * @since 9 Oct 2025
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomManagementService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;



    @Override
    public RoomDto create(RoomDto roomDto) {
        if (roomRepository.existsByTitle(roomDto.title())) {
            throw new DuplicateRoomTitleException(roomDto.title());
        }
        var instance = roomMapper.toEntity(roomDto);
        instance = roomRepository.save(instance);
        return roomMapper.toDto(instance);
    }

    @Override
    public RoomDto update(String roomId, RoomDto roomDto) throws RoomNotFoundException {
        var existsRoom = findRoomById(roomId);
        if (!existsRoom.getTitle().equalsIgnoreCase(roomDto.title())
                && roomRepository.existsByTitle(roomDto.title())) {
            throw new DuplicateRoomTitleException(roomDto.title());
        }
        existsRoom.setTitle(roomDto.title());
        existsRoom = roomRepository.save(existsRoom);
        return roomMapper.toDto(existsRoom);
    }

    private Room findRoomById(String roomId) throws RoomNotFoundException {
        return roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    @Override
    public RoomDto get(String roomId) throws RoomNotFoundException {
        var room = findRoomById(roomId);
        return roomMapper.toDto(room);
    }

    @Override
    public void delete(String roomId) throws RoomNotFoundException {
        try {
            roomRepository.deleteById(roomId);
        } catch (Exception ex) {
            throw new RoomNotFoundException(roomId);
        }
    }
}
