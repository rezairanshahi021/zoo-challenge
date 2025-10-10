package org.iranshahi.zoochallenge.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


/**
 * Represents an animal that can be placed into or associated with one or more {@link Room}s.
 * <p>
 * Each {@code Animal} is persisted in DB and can have:
 * <ul>
 *   <li>A current {@link #roomId} representing its current physical location</li>
 *   <li>A set of favourite rooms ({@link #favouriteRoomIds}) the animal prefers</li>
 *   <li>Date information for when it was first located in the zoo ({@link #located})</li>
 * </ul>
 * </p>
 *
 * <h3>Persistence</h3>
 * <ul>
 *   <li>Collection name: <b>animals</b></li>
 *   <li>Audited for creation and last modification timestamps</li>
 * </ul>
 *
 * <h3>Indexes</h3>
 * <ul>
 *   <li>{@code roomId} — to efficiently query animals by current room</li>
 *   <li>{@code favouriteRoomIds} — for aggregation and reporting</li>
 *   <li>{@code title} — for search/sorting</li>
 * </ul>
 *
 * <h3>Domain Constraints</h3>
 * <ul>
 *   <li>An animal can belong to at most one room at a time</li>
 *   <li>Favourite rooms are represented as a set of room IDs (no duplicates)</li>
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
public class Animal {

    @Id
    @Field(targetType = FieldType.STRING)
    private String id;

    @Indexed
    private String title;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant updated;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate located;

    @Indexed
    private String roomId;

    @Indexed
    private Set<String> favouriteRoomIds = new HashSet<>();
}
