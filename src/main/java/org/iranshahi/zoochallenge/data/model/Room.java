package org.iranshahi.zoochallenge.data.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a physical or logical room within the zoo facility where animals are housed.
 *
 * <p>
 * Each {@code Room} defines a bounded capacity that determines how many animals (by their
 * total {@link org.iranshahi.zoochallenge.data.model.Animal#getVolume() volume}) can occupy
 * it at any given time. Rooms may also enforce a category constraint, ensuring that only
 * animals of a single {@link Category} type are placed together.
 * </p>
 *
 * <h3>Persistence</h3>
 * <ul>
 *   <li>MongoDB collection: <b>rooms</b></li>
 *   <li>Each room has a unique {@link #id}</li>
 *   <li>Auditing fields for creation and modification timestamps are inherited from {@link AuditEntity}</li>
 *   <li>Concurrent updates are protected by optimistic locking using {@link #version}</li>
 * </ul>
 *
 * <h3>Indexes</h3>
 * <ul>
 *   <li>{@link #title} — for efficient room search and sorting</li>
 * </ul>
 *
 * <h3>Domain Fields</h3>
 * <ul>
 *   <li>{@link #capacity} — the maximum total volume of animals that can fit in this room</li>
 *   <li>{@link #usedVolume} — the currently occupied volume by animals</li>
 *   <li>{@link #animalIds} — identifiers of animals currently residing in this room</li>
 *   <li>{@link #allowedCategory} — restricts the type of animals allowed (e.g., DOMESTIC, WILD)</li>
 * </ul>
 *
 * <h3>Domain Constraints</h3>
 * <ul>
 *   <li>{@code usedVolume} must never exceed {@code capacity}</li>
 *   <li>{@code allowedCategory} cannot be {@code null} once animals are present in the room</li>
 *   <li>{@link #animalIds} must contain unique identifiers only</li>
 *   <li>Concurrent modifications are handled via optimistic locking ({@link #version})</li>
 * </ul>
 *
 * <h3>Relationships</h3>
 * <ul>
 *   <li>Referenced by {@link org.iranshahi.zoochallenge.data.model.Animal#getRoomId()}</li>
 *   <li>May also appear in {@link org.iranshahi.zoochallenge.data.model.Animal#getFavouriteRoomIds()}</li>
 * </ul>
 *
 * @author Reza Iranshahi
 * @see org.iranshahi.zoochallenge.data.repository.RoomRepository
 * @since 9 Oct 2025
 */
@Document(collection = "rooms")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Room extends AuditEntity {
    @Id
    @Field(targetType = FieldType.STRING)
    private String id;

    @Indexed
    private String title;

    @Min(1)
    private double capacity;

    private double usedVolume = 0.0;

    private Set<String> animalIds = new HashSet<>();

    @NotNull
    private Category allowedCategory;

    @Version
    private Long version;


}
