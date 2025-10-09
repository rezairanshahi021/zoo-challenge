package org.iranshahi.zoochallenge.web.rest;

import lombok.RequiredArgsConstructor;
import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.business.dto.AnimalPlacementDto;
import org.iranshahi.zoochallenge.business.service.AnimalPlacementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animals/{animal-id}/placement")
@RequiredArgsConstructor
public class AnimalPlacementRestApi {
    private final AnimalPlacementService animalPlacementService;

    @PatchMapping
    public ResponseEntity<AnimalDto> place(@PathVariable("animal-id") String id, @RequestBody AnimalPlacementDto placementDto) {
        return ResponseEntity.ok(animalPlacementService.place(id, placementDto.roomId()));
    }


    @DeleteMapping
    public ResponseEntity<AnimalDto> removeFromRoom(@PathVariable("animal-id") String id) {
        return ResponseEntity.ok(animalPlacementService.remove(id));
    }
}
