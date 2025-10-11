package org.iranshahi.zoochallenge.business.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import org.iranshahi.zoochallenge.data.model.Category;

import java.time.LocalDate;
import java.util.Set;

/**
 * {@code AnimalDto} is a Data Transfer Object used to carry {@link org.iranshahi.zoochallenge.data.model.Animal Animal} data
 * between the resource (API) layer and the business/service layer.
 *
 * <p>
 * This DTO is designed to be <b>immutable</b> (using Java {@code record}), which improves thread-safety
 * and reduces boilerplate in serialization and mapping.
 * </p>
 *
 * <h3>Fields</h3>
 * <ul>
 *   <li>{@link #id} — Unique identifier of the animal</li>
 *   <li>{@link #title} — Human-readable name of the animal</li>
 *   <li>{@link #volume} — The space/volume this animal occupies in a room</li>
 *   <li>{@link #category} — Type/category of the animal (e.g., DOMESTIC, WILD, BIRD)</li>
 *   <li>{@link #located} — Date the animal was first located in the zoo</li>
 *   <li>{@link #roomId} — Identifier of the room the animal currently occupies (nullable)</li>
 *   <li>{@link #favouriteRoomIds} — Set of room IDs marked as this animal’s favourites</li>
 *   <li>{@link #version} — Optimistic lock version for concurrency control</li>
 * </ul>
 *
 * <h3>Validation Rules</h3>
 * <ul>
 *   <li>{@code title}: must not be blank and must have a length between 2 and 50 characters</li>
 *   <li>{@code volume}: must be greater than or equal to 1</li>
 *   <li>{@code category}: must not be null</li>
 *   <li>{@code located}: must not be null</li>
 * </ul>
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 */
@Builder
public record AnimalDto(

        String id,

        @NotBlank(message = "Title must not be empty")
        @Size(min = 2, max = 50, message = "Title length must be between 2 and 50 characters")
        String title,

        @Min(value = 1, message = "Volume must be at least 1")
        double volume,

        @NotNull(message = "Category must not be null")
        Category category,

        @NotNull(message = "Located date must not be null")
        LocalDate located,

        String roomId,

        Set<String> favouriteRoomIds,

        Long version

) { }
