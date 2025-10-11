package org.iranshahi.zoochallenge.business.mapper;

import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.data.model.Animal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Responsible for converting between {@link Animal} entities and {@link AnimalDto} data objects.
 *
 * <p>
 * Provides safe and immutable conversions used by both the API and business layers.
 * </p>
 *
 * <h3>Usage</h3>
 * <ul>
 *   <li>{@link #toEntity(AnimalDto)} — Converts incoming DTOs to domain entities</li>
 *   <li>{@link #toDto(Animal)} — Converts domain entities to outgoing DTOs</li>
 *   <li>{@link #toDtoPage(org.springframework.data.domain.Page)} — Maps paginated lists</li>
 * </ul>
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 */
@Component
public class AnimalMapper {

    /**
     * Converts an {@link AnimalDto} into an {@link Animal} entity.
     *
     * @param dto the {@link AnimalDto} containing animal data
     * @return a new {@link Animal} instance, or {@code null} if input is null
     */
    public Animal toEntity(AnimalDto dto) {
        if (dto == null) {
            return null;
        }

        return Animal.builder()
                .id(dto.id())
                .title(dto.title())
                .volume(dto.volume())
                .category(dto.category())
                .located(dto.located())
                .roomId(dto.roomId())
                .favouriteRoomIds(Optional.ofNullable(dto.favouriteRoomIds()).orElseGet(HashSet::new))
                .version(dto.version())
                .build();
    }

    /**
     * Converts an {@link Animal} entity into an {@link AnimalDto}.
     *
     * @param animal the {@link Animal} entity to convert
     * @return the corresponding {@link AnimalDto}, or {@code null} if input is null
     */
    public AnimalDto toDto(Animal animal) {
        if (animal == null) {
            return null;
        }

        Set<String> favourites = animal.getFavouriteRoomIds() != null
                ? new HashSet<>(animal.getFavouriteRoomIds())
                : Set.of();

        return new AnimalDto(
                animal.getId(),
                animal.getTitle(),
                animal.getVolume(),
                animal.getCategory(),
                animal.getLocated(),
                animal.getRoomId(),
                favourites,
                animal.getVersion()
        );
    }

    /**
     * Converts a paginated list of {@link Animal} entities into a paginated list of {@link AnimalDto}s.
     *
     * @param animals the paginated list of entities
     * @return a paginated list of DTOs
     */
    public Page<AnimalDto> toDtoPage(Page<Animal> animals) {
        return animals.map(this::toDto);
    }
}
