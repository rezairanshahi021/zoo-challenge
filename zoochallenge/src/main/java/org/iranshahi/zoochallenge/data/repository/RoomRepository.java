package org.iranshahi.zoochallenge.data.repository;

import org.iranshahi.zoochallenge.data.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for accessing and manipulating {@link Room} documents in MongoDB.
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Provides data access for the {@link Room} entity.</li>
 *   <li>Enables automatic query derivation using Spring Data naming conventions.</li>
 *   <li>Supports efficient indexed lookups via the {@code title} field.</li>
 * </ul>
 *
 * <h3>Performance Notes</h3>
 * The {@code title} field is indexed (see {@link Room#title}), which allows
 * {@link #existsByTitle(String)} to execute efficiently even in large datasets.
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 */
public interface RoomRepository extends MongoRepository<Room, String> {
    /**
     * Checks whether a room with the given title already exists in the database.
     *
     * @param title the title of the room to check (case-sensitive match)
     * @return {@code true} if a room with the specified title exists; otherwise {@code false}
     */
    boolean existsByTitle(String title);
}