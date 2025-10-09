package org.iranshahi.zoochallenge.service.unit;

import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.business.mapper.AnimalMapper;
import org.iranshahi.zoochallenge.business.service.AnimalService;
import org.iranshahi.zoochallenge.business.service.impl.AnimalServiceImpl;
import org.iranshahi.zoochallenge.data.model.Animal;
import org.iranshahi.zoochallenge.data.repository.AnimalRepository;
import org.iranshahi.zoochallenge.data.repository.RoomRepository;
import org.iranshahi.zoochallenge.exceptions.AnimalNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AnimalServiceTest {

    private AnimalRepository animalRepository;
    private AnimalMapper animalMapper;
    private RoomRepository roomRepository;
    private AnimalService animalService;

    @BeforeEach
    void setUp() {
        animalRepository = mock(AnimalRepository.class);
        roomRepository = mock(RoomRepository.class);
        animalMapper = new AnimalMapper();
        animalService = new AnimalServiceImpl(animalRepository, animalMapper, roomRepository);
    }

    @Test
    void save_new_animal() {
        var located = LocalDate.now();
        var expected = new AnimalDto("A1", "Tigger", located, null, null);
        var animalEntity = Animal.builder().id("A1").title("Tigger").located(located).build();
        when(animalRepository.save(any())).thenReturn(animalEntity);

        var result = animalService.create(expected);

        Assertions.assertEquals(expected.id(), result.id());
        Assertions.assertEquals(expected.title(), result.title());
        verify(animalRepository, times(1)).save(any());
    }

    //todo reza add tests for create animal if there are any other logics

    @Test
    void update_existing_animal() {
        var animalId = "A1";
        var located = LocalDate.of(2025, 5, 1);
        var expected = new AnimalDto("A1", "Lion", located, null, null);
        var entity = Animal.builder().id("A1").title("Tigger").located(located).build();
        when(animalRepository.findById(animalId)).thenReturn(Optional.of(entity));
        when(animalRepository.save(any())).thenReturn(entity);

        var result = animalService.update(animalId, expected);

        Assertions.assertEquals(expected.id(), result.id());
        Assertions.assertEquals(expected.title(), result.title());
    }

    @Test
    void update_not_exists_animal() {
        var animalId = "A9";
        var animalDto = new AnimalDto("A1", "Lion", LocalDate.now(), null, null);

        when(animalRepository.findById(animalId)).thenReturn(Optional.empty());

        Assertions.assertThrows(AnimalNotFoundException.class, () ->
                animalService.update(animalId, animalDto)
        );
    }



}
