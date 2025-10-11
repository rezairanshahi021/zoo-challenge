package org.iranshahi.zoochallenge.integration;

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
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalManagementRestApiTest extends AbstractIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    AnimalRepository animalRepository;

    @BeforeEach
    void setup() {
        animalRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    void get_list_of_animals() throws Exception {

        var room = Room.builder()
                .title("Jungle")
                .usedVolume(20.0)
                .capacity(50.0)
                .allowedCategory(Category.WILD)
                .build();
        room = roomRepository.save(room);

        var animal = Animal.builder()
                .title("Lion")
                .roomId(room.getId())
                .volume(10.0)
                .category(Category.WILD)
                .located(LocalDate.now())
                .build();
         animalRepository.save(animal);

        mockMvc.perform(get("/api/animals/by-room/{roomId}", room.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Lion"))
                .andExpect(jsonPath("$.content[0].roomId").value(room.getId()));
    }
}
