package org.iranshahi.zoochallenge.exceptions;


/**
 * <p>
 * Throws when there aren't any Room with the specific filter item, at this moment we only fetch Room by id
 * </p>
 * <h2>Usage Example</h2>
 * <pre>{@code
 * if (!roomRepository.existsById(id)) {
 *     throw new RoomNotFoundException(id);
 * }
 * }</pre>
 *
 * @author Reza Iranshahi
 * @version 1.0.0
 * @since 9 Oct 2025
 */

public final class RoomNotFoundException extends ZooException {
    public RoomNotFoundException(String id) {
        super("Room with id %s not found".formatted(id), ExceptionCode.ROOM_NOT_FOUND);
    }
}
