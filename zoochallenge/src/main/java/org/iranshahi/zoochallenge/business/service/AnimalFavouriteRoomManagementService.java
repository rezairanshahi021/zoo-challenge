package org.iranshahi.zoochallenge.business.service;

import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.exceptions.AnimalNotFoundException;
import org.iranshahi.zoochallenge.exceptions.RoomNotFoundException;

/**
 * manages all methods for manage animal's favourite room
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 *
 */
public interface AnimalFavouriteRoomManagementService {

    /**
     * adds new room as animal's favourite room
     *
     * @param animalId the ID of animal {@code String}
     * @param roomId   the ID of room {@code String}
     * @return {@link AnimalDto} the updated object of animal contains it's favourite
     * @throws AnimalNotFoundException when animal with id not found
     * @throws RoomNotFoundException   when room with id not found
     */
    AnimalDto addFavouriteRoom(String animalId, String roomId);

    /**
     * deletes an animal's favourite room
     *
     * @param animalId the ID of animal {@code String}
     * @param roomId   the ID of room {@code String}
     * @return {@link AnimalDto} the updated object of animal contains it's favourite
     * @throws AnimalNotFoundException when animal with id not found
     * @throws RoomNotFoundException   when room with id not found
     */
    AnimalDto removeFavouriteRoom(String animalId, String roomId);

}
