package org.iranshahi.zoochallenge.business.service;


import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.exceptions.AnimalNotFoundException;
import org.iranshahi.zoochallenge.exceptions.RoomNotFoundException;

/**
 * manages all placement functionality for animal
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 *
 */
public interface PlacementService {

    /**
     * places an animal to a room
     *
     * @param animalId the ID of animal {@code String}
     * @param roomId   the ID of room {@code String}
     * @return {@link AnimalDto} contains animal data and placement
     * @throws AnimalNotFoundException if animal not exists with the id it will throw
     * @throws RoomNotFoundException   if there isn't a room with the roomId it will throw
     */
    AnimalDto place(String animalId, String roomId);

    /**
     * removes an animal from a room
     *
     * @param animalId the ID of animal {@code String}
     * @return {@link AnimalDto} contains animal data
     * @throws AnimalNotFoundException if animal not exists with the id it will throw
     * @throws RoomNotFoundException   if there isn't a room with the roomId it will throw
     */
    AnimalDto remove(String animalId);

}
