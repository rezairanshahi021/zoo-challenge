package org.iranshahi.zoochallenge.exceptions;

/**
 * <p>
 * if an exists room with the same title is exist then throws this exception
 * </p>
 *
 * @author Reza Iranshahi
 * @version 1.0.0
 * @since 9 Oct 2025
 */

public final class DuplicateRoomTitleException extends ZooException {
    public DuplicateRoomTitleException(String title) {
        super("Room with title %s is already exists".formatted(title), ExceptionCode.ROOM_IS_EXISTS);
    }
}