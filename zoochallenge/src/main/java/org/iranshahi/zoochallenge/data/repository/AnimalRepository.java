package org.iranshahi.zoochallenge.data.repository;

import org.iranshahi.zoochallenge.data.model.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * Repository interface for accessing and managing {@link Animal} documents in MongoDB.
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Expose paginated and sorted access to animals within a given room</li>
 *   <li>Support aggregation and analytics on favourite rooms</li>
 *   <li>Provide a simple API for upper layers (Service/Controller) to interact with MongoDB</li>
 * </ul>
 *
 * <h3>Performance Notes</h3>
 * <ul>
 *   <li>Both {@code roomId} and {@code favouriteRoomIds} are indexed for optimal filtering performance.</li>
 *   <li>Pagination is handled at the database level to avoid unnecessary in-memory processing.</li>
 * </ul>
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 */
public interface AnimalRepository extends MongoRepository<Animal, String> {

    /**
     * Retrieves all animals currently assigned to a specific room.
     *
     * @param roomId   the ID of the room
     * @param pageable pagination and sorting configuration
     * @return a paginated list of animals belonging to the specified room
     */
    Page<Animal> findByRoomId(String roomId, Pageable pageable);

    /**
     * Counts how many animals have the specified room marked as a favourite.
     *
     * @param roomId the ID of the room to count favourites for
     * @return the total number of animals that have this room in their favourites
     */
    long countByFavouriteRoomIdsContaining(String roomId);
}
