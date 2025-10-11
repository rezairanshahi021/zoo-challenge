package org.iranshahi.zoochallenge.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an individual animal entity within the Zoo domain.
 *
 * <p>
 * Each {@code Animal} document models a single creature that can be:
 * <ul>
 *   <li>Placed into exactly one {@link Room} at a time (via {@link #roomId})</li>
 *   <li>Associated with zero or more favourite rooms ({@link #favouriteRoomIds})</li>
 *   <li>Tracked by its physical volume ({@link #volume}) and category type ({@link #category})</li>
 * </ul>
 * </p>
 *
 * <h3>Persistence</h3>
 * <ul>
 *   <li>MongoDB collection: <b>animals</b></li>
 *   <li>Creation and modification timestamps inherited from {@link AuditEntity}</li>
 *   <li>Optimistic locking supported via {@link #version}</li>
 * </ul>
 *
 * <h3>Indexes</h3>
 * <ul>
 *   <li>{@link #roomId} — for efficient lookups of animals within a room</li>
 *   <li>{@link #favouriteRoomIds} — for favourite-room aggregation and reports</li>
 *   <li>{@link #title} — for searching and sorting animals by name</li>
 * </ul>
 *
 * <h3>Domain Constraints</h3>
 * <ul>
 *   <li>An animal can belong to at most one room at a time</li>
 *   <li>The set of favourite rooms ({@link #favouriteRoomIds}) must contain unique identifiers</li>
 *   <li>{@link #volume} must be a positive number and cannot exceed the available room capacity</li>
 *   <li>Version field ensures concurrent modifications are safely resolved (Optimistic Locking)</li>
 * </ul>
 *
 * @author Reza Iranshahi
 * @see org.iranshahi.zoochallenge.data.repository.AnimalRepository
 * @since 9 Oct 2025
 */
@Document(collection = "animals")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Animal extends AuditEntity {

    @Id
    @Field(targetType = FieldType.STRING)
    private String id;

    @Indexed
    private String title;

    @Min(1)
    private double volume;

    private Category category;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate located;

    @Indexed
    private String roomId;

    @Version
    private Long version;

    @Indexed
    private Set<String> favouriteRoomIds = new HashSet<>();
}
