package org.iranshahi.zoochallenge.web.rest;

import lombok.RequiredArgsConstructor;
import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.business.service.AnimalManagementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalManagementRestApi {
    private final AnimalManagementService animalService;

    @PostMapping
    public ResponseEntity<AnimalDto> create(@RequestBody AnimalDto dto) {
        var created = animalService.create(dto);
        return ResponseEntity.created(URI.create("/api/animals/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDto> get(@PathVariable String id) {
        return ResponseEntity.ok().body(animalService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalDto> update(@PathVariable String id, @RequestBody AnimalDto dto) {
        return ResponseEntity.ok(animalService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-room/{roomId}")
    public ResponseEntity<Page<AnimalDto>> animalsInRoom(
            @PathVariable String roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "asc") String order) {

        var dir = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        var pageable = PageRequest.of(page, size, Sort.by(dir, sort));
        return ResponseEntity.ok(animalService.getAnimalsInRoom(roomId, pageable));
    }
}
