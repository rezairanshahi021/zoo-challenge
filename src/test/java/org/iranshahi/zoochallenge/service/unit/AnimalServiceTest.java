package org.iranshahi.zoochallenge.service.unit;

import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.business.mapper.AnimalMapper;
import org.iranshahi.zoochallenge.business.service.AnimalManagementService;
import org.iranshahi.zoochallenge.business.service.impl.AnimalServiceImpl;
import org.iranshahi.zoochallenge.data.model.Animal;
import org.iranshahi.zoochallenge.data.model.Category;
import org.iranshahi.zoochallenge.data.repository.AnimalRepository;
import org.iranshahi.zoochallenge.data.repository.RoomRepository;
import org.iranshahi.zoochallenge.exceptions.AnimalNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AnimalServiceImpl} verifying basic CRUD logic.
 *
 * @author Reza
 * @since 10 Oct 2025
 */
public class AnimalServiceTest {

    private AnimalRepository animalRepository;
    private AnimalMapper animalMapper;
    private AnimalManagementService animalService;

    @BeforeEach
    void setUp() {
        animalRepository = mock(AnimalRepository.class);
        var roomRepository = mock(RoomRepository.class);
        animalMapper = new AnimalMapper();
        animalService = new AnimalServiceImpl(animalRepository, animalMapper, roomRepository);
    }

    @Test
    void save_new_animal() {
        // given
        var located = LocalDate.now();
        var expected = new AnimalDto(
                "animal-1",
                "Tigger",
                10.0,
                Category.WILD,
                located,
                null,
                Set.of(),
                1L
        );

        var animalEntity = animalMapper.toEntity(expected);
        when(animalRepository.save(any())).thenReturn(animalEntity);

        // when
        var result = animalService.create(expected);

        // then
        Assertions.assertEquals(expected.id(), result.id());
        Assertions.assertEquals(expected.title(), result.title());
        Assertions.assertEquals(expected.category(), result.category());
        Assertions.assertEquals(expected.volume(), result.volume());
        verify(animalRepository, times(1)).save(any());
    }

    @Test
    void update_existing_animal() {
        // given
        var animalId = "A1";
        var located = LocalDate.of(2025, 5, 1);
        var expected = new AnimalDto(
                "animal-2",
                "Lion",
                12.5,
                Category.WILD,
                located,
                null,
                Set.of(),
                1L
        );

        var entity = animalMapper.toEntity(expected);
        when(animalRepository.findById(animalId)).thenReturn(Optional.of(entity));
        when(animalRepository.save(any())).thenReturn(entity);

        // when
        var result = animalService.update(animalId, expected);

        // then
        Assertions.assertEquals(expected.id(), result.id());
        Assertions.assertEquals(expected.title(), result.title());
        Assertions.assertEquals(expected.category(), result.category());
        Assertions.assertEquals(expected.volume(), result.volume());
        verify(animalRepository).save(any());
    }

    @Test
    void update_not_exists_animal() {
        var animalId = "A9";
        var animalDto = new AnimalDto(
                "animal-2",
                "Lion",
                8.0,
                Category.WILD,
                LocalDate.now(),
                null,
                Set.of(),
                1L
        );

        when(animalRepository.findById(animalId)).thenReturn(Optional.empty());
        Assertions.assertThrows(AnimalNotFoundException.class, () ->
                animalService.update(animalId, animalDto)
        );
        verify(animalRepository, never()).save(any());
    }
}
