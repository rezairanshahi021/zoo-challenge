package org.iranshahi.zoochallenge.web.rest;

import lombok.RequiredArgsConstructor;
import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.business.service.AnimalPlacementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animals/placement")
@RequiredArgsConstructor
public class AnimalPlacementRestApi {
    private final AnimalPlacementService animalPlacementService;

    @PatchMapping("/{animal-id}/room/{room-id}")
    public ResponseEntity<AnimalDto> place(@PathVariable("animal-id") String id, @RequestParam("room-id") String roomId) {
        return ResponseEntity.ok(animalPlacementService.place(id, roomId));
    }


    @DeleteMapping("/{animal-id}/room")
    public ResponseEntity<AnimalDto> removeFromRoom(@PathVariable("animal-id") String id) {
        return ResponseEntity.ok(animalPlacementService.remove(id));
    }
}
