package org.iranshahi.zoochallenge.exceptions;

/**
 * when animal doesn't have any place to locate in this exception will throw
 *
 * @author Reza Iranshahi
 * @since 11 Oct 2025
 *
 */
public final class AnimalNotPlacedException extends ZooBusinessException {
    public AnimalNotPlacedException(String animalId) {
        super("Animal hasn't placed in a room yet [animal id:%s]".formatted(animalId), ExceptionCode.ROOM_IS_FULL);
    }
}
