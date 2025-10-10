package org.iranshahi.zoochallenge.rest.api.integration;

import org.iranshahi.zoochallenge.config.AbstractIntegrationTest;
import org.iranshahi.zoochallenge.data.model.Animal;
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
    RoomRepository roomRepo;
    @Autowired
    AnimalRepository animalRepo;

    @BeforeEach
    void setup() {
        animalRepo.deleteAll();
        roomRepo.deleteAll();
    }

    @Test
    void get_list_of_animals() throws Exception {
        var room = roomRepo.save(Room.builder().title("Jungle").build());
        var roomId = room.getId();

        animalRepo.save(Animal.builder()
                .title("Lion")
                .roomId(roomId)
                .located(LocalDate.now())
                .build());

        mockMvc.perform(get("/api/animals/by-room/{roomId}", roomId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Lion"))
                .andExpect(jsonPath("$.content[0].roomId").value(roomId));
    }
}
