package org.iranshahi.zoochallenge.integration;

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

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomManagementRestApiTest extends AbstractIntegrationTest {

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
    void get_animal_favourite_room() throws Exception {
        var jungle = roomRepository.save(Room.builder().title("Jungle").build());
        var desert = roomRepository.save(Room.builder().title("Desert").build());

        // two animals with Jungle as favourite
        animalRepository.save(Animal.builder().title("Lion").favouriteRoomIds(Set.of(jungle.getId())).build());
        animalRepository.save(Animal.builder().title("Tiger").favouriteRoomIds(Set.of(jungle.getId())).build());
        // one with Desert
        animalRepository.save(Animal.builder().title("Camel").favouriteRoomIds(Set.of(desert.getId())).build());

        mockMvc.perform(get("/api/rooms/favourites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.title=='Jungle')].count").value(2))
                .andExpect(jsonPath("$[?(@.title=='Desert')].count").value(1));
    }
}
