package org.iranshahi.zoochallenge.web.rest;

import lombok.RequiredArgsConstructor;
import org.iranshahi.zoochallenge.business.dto.AnimalDto;
import org.iranshahi.zoochallenge.business.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalManagementRestApi {
    private final AnimalService animalService;

    @PostMapping
    public ResponseEntity<AnimalDto> create(@RequestBody AnimalDto a) {
        var created = animalService.create(a);
        return ResponseEntity.created(URI.create("/api/animals/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDto> get(@PathVariable String id) {
        return ResponseEntity.ok().body(animalService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalDto> update(@PathVariable String id, @RequestBody AnimalDto a) {
        return ResponseEntity.ok(animalService.update(id, a));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
