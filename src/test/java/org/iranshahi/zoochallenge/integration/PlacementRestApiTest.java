package org.iranshahi.zoochallenge.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.iranshahi.zoochallenge.business.dto.PlacementDto;
import org.iranshahi.zoochallenge.config.AbstractIntegrationTest;
import org.iranshahi.zoochallenge.data.model.Animal;
import org.iranshahi.zoochallenge.data.model.Category;
import org.iranshahi.zoochallenge.data.model.Room;
import org.iranshahi.zoochallenge.data.repository.AnimalRepository;
import org.iranshahi.zoochallenge.data.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlacementRestApiTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AnimalRepository animalRepository;

    private Room room;
    private List<Animal> animals;

    @BeforeEach
    void setup() {
        roomRepository.deleteAll();
        animalRepository.deleteAll();

    }


    @Test
    public void place_animal_successfully() throws Exception {
        var animal = new Animal();
        animal.setTitle("Dog");
        animal.setCategory(Category.DOMESTIC);
        animal.setVolume(10.0);
        animal = animalRepository.save(animal);

        var room = new Room();
        room.setTitle("Room-A");
        room.setCapacity(50.0);
        roomRepository.save(room);

        var dto = new PlacementDto(room.getId());
        var body = objectMapper.writeValueAsString(dto);

        mockMvc.perform(patch("/api/animals/{id}/placement", animal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        var updatedAnimal = animalRepository.findById(animal.getId()).orElseThrow();
        var updatedRoom = roomRepository.findById(room.getId()).orElseThrow();

        assertThat(updatedAnimal.getRoomId()).isEqualTo(room.getId());
        assertThat(updatedRoom.getUsedVolume()).isEqualTo(10.0);
        assertThat(updatedRoom.getAllowedCategory()).isEqualTo(Category.DOMESTIC);
    }


    @Test
    public void place_animal_exceeds_capacity() throws Exception {
        Animal animal = new Animal();
        animal.setTitle("Elephant");
        animal.setCategory(Category.DOMESTIC);
        animal.setVolume(100.0);
        animal = animalRepository.save(animal);

        Room room = new Room();
        room.setTitle("Small Room");
        room.setCapacity(50.0);
        roomRepository.save(room);

        var dto = new PlacementDto(room.getId());
        String body = objectMapper.writeValueAsString(dto);

        mockMvc.perform(patch("/api/animals/{id}/placement", animal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());


        Animal reloaded = animalRepository.findById(animal.getId()).orElseThrow();
        assertThat(reloaded.getRoomId()).isNull();
    }


    @Test
    public void place_animal_with_wrong_category() throws Exception {
        Room room = new Room();
        room.setTitle("Domestic Room");
        room.setCapacity(50.0);
        room.setAllowedCategory(Category.DOMESTIC);
        roomRepository.save(room);

        Animal animal = new Animal();
        animal.setTitle("Tiger");
        animal.setCategory(Category.WILD);
        animal.setVolume(10.0);
        animal = animalRepository.save(animal);

        var dto = new PlacementDto(room.getId());
        String body = objectMapper.writeValueAsString(dto);

        mockMvc.perform(patch("/api/animals/{id}/placement", animal.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());


        Animal reloaded = animalRepository.findById(animal.getId()).orElseThrow();
        assertThat(reloaded.getRoomId()).isNull();

        Room unchangedRoom = roomRepository.findById(room.getId()).orElseThrow();
        assertThat(unchangedRoom.getUsedVolume()).isEqualTo(0.0);
    }


    @Test
    public void place_animal_concurrent() throws Exception {

        room = Room.builder()
                .title("Concurrent Room")
                .capacity(30.0)
                .usedVolume(0.0)
                .build();
        roomRepository.save(room);

        animals = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Animal a = Animal.builder()
                    .title("Animal-" + i)
                    .category(Category.DOMESTIC)
                    .volume(10.0)
                    .build();
            animals.add(animalRepository.save(a));
        }


        int numThreads = animals.size();
        var latch = new CountDownLatch(1);
        try (var executor = Executors.newFixedThreadPool(numThreads)) {

            var placementDto = new PlacementDto(room.getId());
            String body = objectMapper.writeValueAsString(placementDto);

            List<Callable<Integer>> tasks = animals.stream()
                    .map(a -> (Callable<Integer>) () -> {
                        latch.await();
                        var result = mockMvc.perform(
                                        patch("/api/animals/{id}/placement", a.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body)
                                )
                                .andReturn();
                        return result.getResponse().getStatus();
                    })
                    .toList();

            for (Callable<Integer> task : tasks) executor.submit(task);
            latch.countDown();
            executor.shutdown();

            Room finalRoom = roomRepository.findById(room.getId()).orElseThrow();
            List<Animal> placedAnimals = animalRepository.findAll().stream()
                    .filter(a -> room.getId().equals(a.getRoomId()))
                    .toList();

            assertThat(finalRoom.getUsedVolume()).isLessThanOrEqualTo(finalRoom.getCapacity() + 0.01);
            assertThat(placedAnimals.size()).isEqualTo((int) (finalRoom.getUsedVolume() / 10.0));
            assertThat(placedAnimals.size()).isLessThanOrEqualTo(3);
        }
    }
}
