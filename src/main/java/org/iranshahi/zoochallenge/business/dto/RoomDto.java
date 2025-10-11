package org.iranshahi.zoochallenge.business.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.iranshahi.zoochallenge.data.model.Category;

import java.util.Set;

/**
 * {@code RoomDto} is a Data Transfer Object used to carry
 * {@link org.iranshahi.zoochallenge.data.model.Room Room} data
 * between the resource (API) layer and the business/service layer.
 *
 * <p>
 * This DTO is <b>immutable</b> (implemented as a Java {@code record}),
 * which improves thread-safety and reduces boilerplate during serialization and mapping.
 * </p>
 *
 * <h3>Fields</h3>
 * <ul>
 *   <li>{@link #id} — Unique identifier of the room</li>
 *   <li>{@link #title} — Human-readable name of the room</li>
 *   <li>{@link #capacity} — Maximum total volume that can be occupied by animals</li>
 *   <li>{@link #usedVolume} — Currently used portion of the capacity</li>
 *   <li>{@link #animalIds} — Set of animal identifiers currently assigned to the room</li>
 *   <li>{@link #allowedCategory} — Restricts which animal category is allowed in this room</li>
 *   <li>{@link #version} — Optimistic lock version used for concurrency control</li>
 * </ul>
 *
 * <h3>Validation Rules</h3>
 * <ul>
 *   <li>{@code title}: must not be blank and length between 2–50 characters</li>
 *   <li>{@code capacity}: must be a positive number greater than or equal to 1</li>
 *   <li>{@code allowedCategory}: must not be null when creating a room</li>
 * </ul>
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 */
@Builder
public record RoomDto(
        String id,

        @NotBlank(message = "Title must not be empty")
        @Size(min = 2, max = 50, message = "Title length must be between 2 and 50 characters")
        String title,

        @Min(value = 1, message = "Capacity must be at least 1")
        double capacity,

        double usedVolume,

        Set<String> animalIds,

        @NotNull(message = "Allowed category must not be null")
        Category allowedCategory,

        Long version
) { }
