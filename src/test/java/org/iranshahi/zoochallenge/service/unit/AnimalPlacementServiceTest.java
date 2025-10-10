package org.iranshahi.zoochallenge.service.unit;

import org.iranshahi.zoochallenge.business.mapper.AnimalMapper;
import org.iranshahi.zoochallenge.business.service.AnimalPlacementService;
import org.iranshahi.zoochallenge.business.service.impl.AnimalServiceImpl;
import org.iranshahi.zoochallenge.data.model.Animal;
import org.iranshahi.zoochallenge.data.model.Room;
import org.iranshahi.zoochallenge.data.repository.AnimalRepository;
import org.iranshahi.zoochallenge.data.repository.RoomRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnimalPlacementServiceTest {


    @Test
    void place_animal_in_room() {
        var animalRepo = mock(AnimalRepository.class);
        var roomRepo = mock(RoomRepository.class);
        var mapper = new AnimalMapper();
        AnimalPlacementService animalPlacementService = new AnimalServiceImpl(animalRepo, mapper, roomRepo);

        var a = Animal.builder().id("a1").title("Tiger").build();
        when(animalRepo.findById("a1")).thenReturn(Optional.of(a));
        var room = Room.builder().id("r1").title("Green").build();
        when(roomRepo.findById("r1")).thenReturn(Optional.of(room));
        when(animalRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var res = animalPlacementService.place("a1", "r1");
        assertEquals("r1", res.roomId());
    }



}
