package org.iranshahi.zoochallenge.web.rest;

import lombok.RequiredArgsConstructor;
import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.business.dto.AnimalFavouriteRoomDto;
import org.iranshahi.zoochallenge.business.service.AnimalFavouriteRoomManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animals/{animal-id}/favourites")
@RequiredArgsConstructor
public class AnimalFavouriteRestApi {

    private final AnimalFavouriteRoomManagementService animalFavouriteRoomManagementService;

    @PostMapping()
    public ResponseEntity<AnimalDto> addFav(@PathVariable("animal-id") String id, @RequestBody AnimalFavouriteRoomDto dto) {
        return ResponseEntity.ok(animalFavouriteRoomManagementService.addFavouriteRoom(id, dto.roomId()));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<AnimalDto> removeFav(@PathVariable("animal-id") String id, @PathVariable String roomId) {
        return ResponseEntity.ok(animalFavouriteRoomManagementService.removeFavouriteRoom(id, roomId));
    }

}
