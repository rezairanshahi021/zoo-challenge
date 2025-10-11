package org.iranshahi.zoochallenge.exceptions;


/**
 *
 * @author Reza Iranshahi
 * @since 11 Oct 2025
 * */
public final class RoomOutOfSpaceException extends ZooBusinessException {
    public RoomOutOfSpaceException(String roomId) {
        super("Room is full [room id:%s]".formatted(roomId), ExceptionCode.ROOM_IS_FULL);
    }
}
