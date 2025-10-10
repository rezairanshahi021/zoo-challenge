package org.iranshahi.zoochallenge.business.service;


import org.iranshahi.zoochallenge.business.dto.RoomDto;
import org.iranshahi.zoochallenge.exceptions.DuplicateRoomTitleException;
import org.iranshahi.zoochallenge.exceptions.RoomNotFoundException;

/**
 * Exposes all required functionality to manage {@code Room}
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 *
 */
public interface RoomManagementService {


    /**
     * creates a new {@code Room}
     *
     * @param roomDto the new room data {@link RoomDto}, <tt>this object is contains room title</tt>
     * @return saved room object {@link RoomDto} contains id, and title
     * @throws DuplicateRoomTitleException if there is an exist room with the same title it throws
     */
    RoomDto create(RoomDto roomDto);

    /**
     * updates an exists {@code Room}
     *
     * @param roomId  the exists room id
     * @param roomDto @link RoomDto} new title of the room
     * @return updated room data {@link RoomDto} contains id, and title
     * @throws RoomNotFoundException       if there isn't a room with the roomId it will throw
     * @throws DuplicateRoomTitleException if there is an exist room with the same title it throws
     *
     */
    RoomDto update(String roomId, RoomDto roomDto) throws RoomNotFoundException, DuplicateRoomTitleException;

    /**
     * gets an exists room by id
     *
     * @param roomId the ID of room
     * @return exists room {@link RoomDto}
     * @throws RoomNotFoundException if there isn't a room with the roomId it will throw
     *
     */
    RoomDto get(String roomId) throws RoomNotFoundException;

    /**
     * deletes an exists room
     *
     * @param roomId the ID of room
     * @throws RoomNotFoundException if there isn't a room with the roomId it will throw
     */
    void delete(String roomId) throws RoomNotFoundException;


}
