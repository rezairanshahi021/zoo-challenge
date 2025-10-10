package org.iranshahi.zoochallenge.service.unit;

import org.iranshahi.zoochallenge.business.mapper.AnimalMapper;
import org.iranshahi.zoochallenge.business.service.AnimalFavouriteRoomManagementService;
import org.iranshahi.zoochallenge.business.service.impl.AnimalServiceImpl;
import org.iranshahi.zoochallenge.data.model.Animal;
import org.iranshahi.zoochallenge.data.model.Room;
import org.iranshahi.zoochallenge.data.repository.AnimalRepository;
import org.iranshahi.zoochallenge.data.repository.RoomRepository;
import org.iranshahi.zoochallenge.exceptions.RoomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnimalFavRoomManagementServiceTest {

    private AnimalRepository animalRepository;
    private RoomRepository roomRepository;
    private AnimalFavouriteRoomManagementService animalService;

    @BeforeEach
    void setUp() {
        animalRepository = mock(AnimalRepository.class);
        roomRepository = mock(RoomRepository.class);
        AnimalMapper animalMapper = new AnimalMapper();
        animalService = new AnimalServiceImpl(animalRepository, animalMapper, roomRepository);
    }

    @Test
    void add_favourite_room_for_animal() {
        var animal = Animal.builder().id("a1").favouriteRoomIds(new HashSet<>()).build();
        var room = Room.builder().id("r1").title("Desert").build();
        when(animalRepository.findById("a1")).thenReturn(Optional.of(animal));
        when(roomRepository.findById("r1")).thenReturn(Optional.of(room));
        when(animalRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var result = animalService.addFavouriteRoom("a1", "r1");
        assertTrue(result.favouriteRoomIds().contains("r1"));
    }

    @Test
    void room_notFound_while_adding_to_animal_favourite() {
        Animal a = Animal.builder().id("a1").build();
        when(animalRepository.findById("a1")).thenReturn(Optional.of(a));
        when(roomRepository.findById("r404")).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class,
                () -> animalService.addFavouriteRoom("a1", "r404"));
    }
}
