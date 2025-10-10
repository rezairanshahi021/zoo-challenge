package org.iranshahi.zoochallenge.web.rest;

import lombok.RequiredArgsConstructor;
import org.iranshahi.zoochallenge.business.dto.FavouriteRoomDto;
import org.iranshahi.zoochallenge.business.dto.RoomDto;
import org.iranshahi.zoochallenge.business.service.FavouriteRoomReportingService;
import org.iranshahi.zoochallenge.business.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 *
 * @author Reza Iranshahi
 * @since 9 Oct 2025
 *
 */
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomManagementRestApi {
    private final RoomService roomService;
    private final FavouriteRoomReportingService reportingService;


    @PostMapping
    public ResponseEntity<RoomDto> create(@RequestBody RoomDto roomDto) {
        var created = roomService.create(roomDto);
        return ResponseEntity.created(URI.create("/api/rooms/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> get(@PathVariable String id) {
        var room = roomService.get(id);
        return ResponseEntity.ok().body(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> update(@PathVariable String id, @RequestBody RoomDto roomDto) {
        var updated = roomService.update(id, roomDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/favourites")
    public ResponseEntity<List<FavouriteRoomDto>> favourites() {
        return ResponseEntity.ok(reportingService.listFavouriteRooms());
    }

}
