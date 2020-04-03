package nl.tudelft.oopp.demo.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import nl.tudelft.oopp.demo.DemoApplication;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Room;
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
class RoomControllerTest {

    @Autowired
    WebApplicationContext context;

    private MockMvc mockMvc;

    private String roomCode;
    private String name;
    private Integer capacity;
    private boolean hasWhiteboard;
    private boolean hasTV;
    private Integer rights;
    private Building building;
    private Room room;

    @BeforeEach()
    public void setup() throws Exception {
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
        building = new Building(123, "Test Building", "Somewhere", "11:11-22:22", 42);

        room = new Room(roomCode, name, capacity, hasWhiteboard, hasTV, rights, building);


    }

    @WithMockUser(authorities = "Admin")
    public void connectionTest(String url) throws Exception {
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void addToDatabaseTest() throws Exception {
        mockMvc.perform(post("/buildings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(building)));

        String url = "/rooms";
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(room)))
                .andExpect(status().isOk());
        connectionTest(url);
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFilteredRoomsTest() throws Exception {
        addToDatabaseTest();
        String url = "/rooms/getFilteredRooms/";
        mockMvc.perform(get(url)).andExpect(status().isOk()).andDo(print());
        String response = mockMvc.perform(get(url)).andReturn().getResponse().getContentAsString();
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllTest() throws Exception {
        addToDatabaseTest();
        String url = "/rooms/all?search=roomCode:69";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("room"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void testGetAll() throws Exception {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsFromBuilding() throws Exception {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void addNewRoom() throws Exception {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomNamesFromBuilding() throws Exception {
        addToDatabaseTest();
        String url = "/rooms/getAllRoomsFromBuilding?search=building.buildingCode:123 AND rights<3";
        mockMvc.perform(get(url).param("buildingCode", "123").param("rights", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"roomCode\":\"69\",\"name\":\"TestRoom\",\""
                        + "capacity\":69,\"hasWhiteboard\":true,\"hasTV\":true,\"rights\":2,\""
                        + "building\":{\"buildingCode\":123,\"name\":\"Test Building\",\"location\""
                        + ":\"Somewhere\",\"openingHours\":\"11:11-22:22\",\"bikes\":42}}]"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithTV() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithCapacity() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithTv() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithWhiteBoard() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFilteredRoomsFromBuilding() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllFilteredRooms() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void saveRoom() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void updateRoom() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteRoom() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void uploadFile() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getUrl() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void downloadFile() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteImage() {
    }
}