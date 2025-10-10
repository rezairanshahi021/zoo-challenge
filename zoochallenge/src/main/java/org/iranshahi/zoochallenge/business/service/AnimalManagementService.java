package org.iranshahi.zoochallenge.business.service;

import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.exceptions.AnimalNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Exposes all required functionality to manage {@code Animal}
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 *
 */
public interface AnimalManagementService {


    /**
     * creates a new {@code Animal}
     *
     * @param animalDto the new animal data {@link AnimalDto}
     * @return saved animal object {@link AnimalDto}
     */
    AnimalDto create(AnimalDto animalDto);

    /**
     * updates location and title of an animal
     *
     * @param animalId  the ID of animal {@code String}
     * @param animalDto {@link AnimalDto} the dto object contains located and title
     * @return updated data {@link AnimalDto}
     * @throws AnimalNotFoundException if animal not exists with the id it will throw
     */
    AnimalDto update(String animalId, AnimalDto animalDto) throws AnimalNotFoundException;

    /**
     * gets animal by id
     *
     * @param animalId the ID of animal {@code String}
     * @return animal data {@link AnimalDto}
     * @throws AnimalNotFoundException if animal not exists with the id it will throw
     */
    AnimalDto get(String animalId);


    /**
     * deletes animal by id
     *
     * @param animalId the ID of animal {@code String}
     * @throws AnimalNotFoundException if animal not exists with the id it will throw
     */
    void delete(String animalId);


    /**
     *
     * gets all animals that are living in a specific room
     *
     * @param roomId   the ID of room {@code String}
     * @param pageable handles page size and number and sorting concerns {@link Pageable}
     * @return list of animals in the room by page
     */
    Page<AnimalDto> getAnimalsInRoom(String roomId, Pageable pageable);
}
