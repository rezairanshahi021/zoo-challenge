package org.iranshahi.zoochallenge.business.mapper;

import org.iranshahi.zoochallenge.business.dto.RoomDto;
import org.iranshahi.zoochallenge.data.model.Room;
import org.springframework.stereotype.Component;

/**
 * Responsible to maps RoomDto to Room and Room to RoomDto
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 *
 */
@Component
public class RoomMapper {

    /**
     * maps {@link RoomDto} to {@link Room}
     * <p>
     * At this moment it only set title to Room.
     * </p>
     *
     * @param dto {@link RoomDto} contains title
     * @return generated {@link Room}  entity
     *
     */
    public Room toEntity(RoomDto dto) {
        return Room.builder().title(dto.title()).build();
    }

    /**
     * maps {@link Room} to {@link RoomDto}
     * <p>
     * At this moment it only set title, and id to RoomDto.
     * </p>
     *
     * @param room {@link Room} contains title, and id
     * @return {@link RoomDto} object
     *
     */
    public RoomDto toDto(Room room) {
        return new RoomDto(room.getId(), room.getTitle());
    }
}
