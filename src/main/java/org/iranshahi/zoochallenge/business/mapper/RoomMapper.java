package org.iranshahi.zoochallenge.business.mapper;

import org.iranshahi.zoochallenge.business.dto.RoomDto;
import org.iranshahi.zoochallenge.data.model.Room;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Responsible for mapping between {@link Room} entity and {@link RoomDto} objects.
 * <p>
 * Provides conversion methods used by both the resource (API) and business layers.
 * </p>
 *
 * <h3>Usage</h3>
 * <ul>
 *   <li>{@link #toEntity(RoomDto)} — Converts a DTO (typically from API input) into a Room entity</li>
 *   <li>{@link #toDto(Room)} — Converts a Room entity into a DTO (typically for API responses)</li>
 * </ul>
 *
 * <p>
 * This mapper does <b>not</b> persist data. It only converts between representations.
 * </p>
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 */
@Component
public class RoomMapper {

    /**
     * Converts a {@link RoomDto} into a {@link Room} entity.
     * <p>
     * The {@code id} and {@code version} fields are typically managed by the database,
     * so they are not set here unless provided explicitly.
     * </p>
     *
     * @param dto the {@link RoomDto} carrying API or external data
     * @return a new {@link Room} instance
     */
    public Room toEntity(RoomDto dto) {
        if (dto == null) {
            return null;
        }

        return Room.builder()
                .id(dto.id())
                .title(dto.title())
                .capacity(dto.capacity())
                .usedVolume(dto.usedVolume())
                .animalIds(Optional.ofNullable(dto.animalIds()).orElseGet(HashSet::new))
                .allowedCategory(dto.allowedCategory())
                .version(dto.version())
                .build();
    }

    /**
     * Converts a {@link Room} entity into a {@link RoomDto}.
     * <p>
     * Used for API responses or transferring data between layers.
     * </p>
     *
     * @param room the {@link Room} entity to convert
     * @return the mapped {@link RoomDto} or {@code null} if input is null
     */
    public RoomDto toDto(Room room) {
        if (room == null) {
            return null;
        }

        Set<String> animalIdsCopy = room.getAnimalIds() != null
                ? new HashSet<>(room.getAnimalIds())
                : Set.of();

        return new RoomDto(
                room.getId(),
                room.getTitle(),
                room.getCapacity(),
                room.getUsedVolume(),
                animalIdsCopy,
                room.getAllowedCategory(),
                room.getVersion()
        );
    }
}
