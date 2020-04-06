package nl.tudelft.oopp.demo.controllers;

import com.google.gson.Gson;
import nl.tudelft.oopp.demo.DemoApplication;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.FavoriteRoom;
import nl.tudelft.oopp.demo.entities.Room;
import nl.tudelft.oopp.demo.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {
        DemoApplication.class,
        SecurityConfiguration.class,
        PlatformTransactionManager.class
})
@Transactional
class FavoriteRoomControllerTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    private String roomCode;
    private String name;
    private Integer capacity;
    private boolean hasWhiteboard;
    private boolean hasTV;
    private Integer rights;
    private Building building;
    private Room room;

    private Integer id;
    private User user;
    private Integer userId;
    private FavoriteRoom favoriteRoom;

    @BeforeEach
    void setUpper() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        roomCode = "69";
        name = "TestRoom";
        capacity = 69;
        hasWhiteboard = true;
        hasTV = true;
        rights = 2;
        building = new Building(55, "Test Building", "Somewhere", "11:11-22:22", 42);
        room = new Room(roomCode, name, capacity, hasWhiteboard, hasTV, rights, building);

        id = 42;
        user = new User("admin", "admin");
        userId = user.getId();
        favoriteRoom = new FavoriteRoom(id, room, user);
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void saveRoom() throws Exception {
        mockMvc.perform(post("/buildings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(building)));

        String url = "/rooms";
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(room)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void saveUser() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void addFavoriteRoomTest() throws Exception {
        saveRoom();
        saveUser();
        mockMvc.perform(post("/favoriterooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(favoriteRoom)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFavoriteRoomsTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteFavoriteRoomTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFavoriteRoomTest() {
    }
}