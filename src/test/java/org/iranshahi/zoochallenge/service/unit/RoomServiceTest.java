package org.iranshahi.zoochallenge.service.unit;

import org.iranshahi.zoochallenge.business.dto.RoomDto;
import org.iranshahi.zoochallenge.business.mapper.RoomMapper;
import org.iranshahi.zoochallenge.business.service.RoomManagementService;
import org.iranshahi.zoochallenge.business.service.impl.RoomServiceImpl;
import org.iranshahi.zoochallenge.data.model.Category;
import org.iranshahi.zoochallenge.data.model.Room;
import org.iranshahi.zoochallenge.data.repository.RoomRepository;
import org.iranshahi.zoochallenge.exceptions.DuplicateRoomTitleException;
import org.iranshahi.zoochallenge.exceptions.RoomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RoomServiceImpl}, verifying CRUD and validation logic.
 *
 * @author Reza
 * @since 10 Oct 2025
 */
public class RoomServiceTest {

    private RoomManagementService roomService;
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        roomRepository = mock(RoomRepository.class);
        RoomMapper roomMapper = new RoomMapper();
        roomService = new RoomServiceImpl(roomRepository, roomMapper);
    }

    @Test
    void create_a_room() {
        // Arrange
        var roomTitle = "Green";
        var dto = new RoomDto(
                null,
                roomTitle,
                30.0,
                0.0,
                new HashSet<>(),
                Category.WILD,
                null
        );

        when(roomRepository.save(any()))
                .thenAnswer(inv -> {
                    Room room = inv.getArgument(0);
                    room.setId("room1");
                    room.setTitle(roomTitle);
                    return room;
                });

        // Act
        var createdRoom = roomService.create(dto);

        // Assert
        assertNotNull(createdRoom.id());
        assertEquals(roomTitle, createdRoom.title());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void create_a_room_with_existing_title() {
        // Arrange
        var roomTitle = "Green";
        var dto = new RoomDto(
                null,
                roomTitle,
                30.0,
                0.0,
                new HashSet<>(),
                Category.WILD,
                null
        );

        when(roomRepository.existsByTitle(roomTitle)).thenReturn(true);

        // Assert
        assertThrows(DuplicateRoomTitleException.class,
                () -> roomService.create(dto)
        );
        verify(roomRepository, never()).save(any());
    }

    @Test
    void update_an_existing_room() {
        // Arrange
        var newTitle = "Red";
        var existing = Room.builder()
                .id("room1")
                .title("Green")
                .capacity(20.0)
                .allowedCategory(Category.WILD)
                .build();

        when(roomRepository.findById("room1")).thenReturn(Optional.of(existing));
        when(roomRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var updatedDto = new RoomDto(
                "room1",
                newTitle,
                20.0,
                0.0,
                new HashSet<>(),
                Category.WILD,
                1L
        );

        // Act
        var result = roomService.update("room1", updatedDto);

        // Assert
        assertEquals(newTitle, result.title());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void update_a_room_with_existing_title() {
        // Arrange
        var dto = new RoomDto(
                null,
                "Red",
                30.0,
                0.0,
                new HashSet<>(),
                Category.DOMESTIC,
                null
        );

        var existing = Room.builder()
                .id("room1")
                .title("Green")
                .allowedCategory(Category.WILD)
                .build();

        when(roomRepository.findById("room1")).thenReturn(Optional.of(existing));
        when(roomRepository.existsByTitle("Red")).thenReturn(true);

        // Assert
        assertThrows(DuplicateRoomTitleException.class,
                () -> roomService.update("room1", dto)
        );
        verify(roomRepository, never()).save(any());
    }

    @Test
    void update_not_existing_room() {
        // Arrange
        when(roomRepository.findById("r99")).thenReturn(Optional.empty());
        var dto = new RoomDto(
                null,
                "Green",
                30.0,
                0.0,
                new HashSet<>(),
                Category.WILD,
                null
        );

        // Assert
        assertThrows(RoomNotFoundException.class,
                () -> roomService.update("r99", dto)
        );
        verify(roomRepository, never()).save(any());
    }

    @Test
    void delete_room_invokes_repository() {
        doNothing().when(roomRepository).deleteById("room1");
        roomService.delete("room1");
        verify(roomRepository, times(1)).deleteById("room1");
    }
}
