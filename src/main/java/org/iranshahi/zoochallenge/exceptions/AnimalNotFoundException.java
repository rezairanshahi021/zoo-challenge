package org.iranshahi.zoochallenge.exceptions;


/**
 * <p>
 * Throws when there aren't any Animal with the specific filter item, at this moment we only fetch Animal by id
 * </p>
 * <h2>Usage Example</h2>
 * <pre>{@code
 * if (!animalRepository.existsById(id)) {
 *     throw new AnimalNotFoundException(id);
 * }
 * }</pre>
 *
 * @author Reza Iranshahi
 * @version 1.0.0
 * @since 9 Oct 2025
 */
public final class AnimalNotFoundException extends ZooException {
    public AnimalNotFoundException(String id) {
        super("Animal with id %s not found".formatted(id), ExceptionCode.ANIMAL_NOT_FOUND);
    }
}
