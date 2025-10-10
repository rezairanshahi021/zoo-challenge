package org.iranshahi.zoochallenge.service.unit;

import org.iranshahi.zoochallenge.business.dto.RoomDto;
import org.iranshahi.zoochallenge.business.mapper.RoomMapper;
import org.iranshahi.zoochallenge.business.service.RoomManagementService;
import org.iranshahi.zoochallenge.business.service.impl.RoomServiceImpl;
import org.iranshahi.zoochallenge.data.model.Room;
import org.iranshahi.zoochallenge.data.repository.RoomRepository;
import org.iranshahi.zoochallenge.exceptions.DuplicateRoomTitleException;
import org.iranshahi.zoochallenge.exceptions.RoomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RoomServiceTest {

    private RoomManagementService roomService;
    private RoomRepository roomRepository;
    private RoomMapper roomMapper;

    @BeforeEach
    void setUp() {
        roomRepository = mock(RoomRepository.class);
        roomMapper = new RoomMapper();
        roomService = new RoomServiceImpl(roomRepository, roomMapper);
    }


    @Test
    void create_a_room() {
        //Arrangement
        var roomTitle = "Green";
        var roomDTO = new RoomDto(null, roomTitle);

        when(roomRepository.save(any()))
                .thenAnswer(inv -> {
                    Room room = inv.getArgument(0);
                    room.setId("room1");
                    room.setTitle(roomTitle);
                    return room;
                });
        //MUT
        var createdRoom = roomService.create(roomDTO);

        //Assertion
        assertNotNull(createdRoom.id());
        assertEquals(roomTitle, createdRoom.title());
    }

    @Test
    void create_a_room_with_existing_title() {
        //Arrangement
        var roomTitle = "Green";
        var roomDTO = new RoomDto(null, roomTitle);
        when(roomRepository.existsByTitle(roomTitle)).thenReturn(Boolean.TRUE);

        //MUT & Assertion
        assertThrows(DuplicateRoomTitleException.class,
                () -> roomService.create(roomDTO));
    }

    @Test
    void update_an_existing_room() {
        var newRoomTitle = "Red";
        var existing = Room.builder().id("room1").title("Green").build();
        when(roomRepository.findById("room1")).thenReturn(Optional.of(existing));
        when(roomRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var updatedDTO = roomMapper.toDto(Room.builder().id("room1").title(newRoomTitle).build());
        var result = roomService.update("room1", updatedDTO);

        assertEquals(newRoomTitle, result.title());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void update_a_room_with_existing_title() {
        var roomDTO = new RoomDto(null, "Red");
        var existing = Room.builder().id("room1").title("Green").build();
        when(roomRepository.findById("room1")).thenReturn(Optional.of(existing));
        when(roomRepository.existsByTitle("Red")).thenReturn(Boolean.TRUE);

        assertThrows(DuplicateRoomTitleException.class, () ->
                roomService.update("room1", roomDTO)
        );
    }

    @Test
    void update_not_existing_room() {
        when(roomRepository.findById("r99")).thenReturn(Optional.empty());
        assertThrows(RoomNotFoundException.class, () -> roomService.update("r99", new RoomDto(null, null)));
    }

    @Test
    void delete_room_invokesRepo() {
        doNothing().when(roomRepository).deleteById("room1");
        roomService.delete("room1");
        verify(roomRepository, times(1)).deleteById("room1");
    }


}
