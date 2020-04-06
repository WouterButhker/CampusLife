package nl.tudelft.oopp.demo.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
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
        favoriteRoom = new FavoriteRoom(id, room, user);
    }

    @WithMockUser(authorities = "Admin")
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
    public void saveUser() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user)));
        String responseString = mockMvc.perform(get("/rest/users/all"))
                .andReturn().getResponse().getContentAsString();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<User>>() {
        }.getType();
        List<User> userList = gson.fromJson(responseString, listType);
        user = userList.get(0);
        favoriteRoom = new FavoriteRoom(id, room, user);
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
    void addFavoriteRoomFoundTest() throws Exception {
        addFavoriteRoomTest();
        mockMvc.perform(post("/favoriterooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(favoriteRoom)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFavoriteRoomsTest() throws Exception {
        addFavoriteRoomTest();
        String url = "/favoriterooms/user/" + user.getId();
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteFavoriteRoomNotFoundTest() throws Exception {
        addFavoriteRoomTest();
        String url = "/favoriterooms/" + favoriteRoom.getId();
        mockMvc.perform(delete(url))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteFavoriteRoomTest() throws Exception {
        addFavoriteRoomTest();

        String responseString = mockMvc.perform(get("/favoriterooms/user/" + user.getId()))
                .andReturn().getResponse().getContentAsString();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<FavoriteRoom>>() {
        }.getType();
        List<FavoriteRoom> favoriteRoomList = gson.fromJson(responseString, listType);
        favoriteRoom = favoriteRoomList.get(0);

        String url = "/favoriterooms/" + favoriteRoom.getId();
        mockMvc.perform(delete(url))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFavoriteRoomTest() throws Exception {
        addFavoriteRoomTest();
        String url = "/favoriterooms/user/" + user.getId() + "/room/" + room.getRoomCode();
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }
}