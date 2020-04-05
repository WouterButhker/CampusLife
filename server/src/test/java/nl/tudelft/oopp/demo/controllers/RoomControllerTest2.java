package nl.tudelft.oopp.demo.controllers;

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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {
        DemoApplication.class,
        SecurityConfiguration.class,
        PlatformTransactionManager.class
})
@Transactional
class RoomControllerTest2 {

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
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"roomCode\":\"69\",\"name\":\"TestRoom\","
                        + "\"capacity\":69,\"hasWhiteboard\":true,\"hasTV\":true,\"rights\":2,\""
                        + "building\":{\"buildingCode\":123,\"name\":\"Test Building\",\"location"
                        + "\":\"Somewhere\",\"openingHours\":\"11:11-22:22\",\"bikes\":42}}]"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllTest() throws Exception {
        addToDatabaseTest();
        String url = "/rooms/all?search=roomCode:69";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"roomCode\":\"69\",\"name\":\"TestRoom\",\""
                        + "capacity\":69,\"hasWhiteboard\":true,\"hasTV\":true,\"rights\":2,\""
                        + "building\":{\"buildingCode\":123,\"name\":\"Test Building\",\"location\""
                        + ":\"Somewhere\",\"openingHours\":\"11:11-22:22\",\"bikes\":42}}]"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsFromBuildingTest() throws Exception {
        addToDatabaseTest();
        String url = "/rooms/getAllRoomsFromBuilding";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"roomCode\":\"69\",\"name\":\"TestRoom\",\""
                        + "capacity\":69,\"hasWhiteboard\":true,\"hasTV\":true,\"rights\":2,\""
                        + "building\":{\"buildingCode\":123,\"name\":\"Test Building\",\"location\""
                        + ":\"Somewhere\",\"openingHours\":\"11:11-22:22\",\"bikes\":42}}]"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomNamesFromBuildingTest() throws Exception {
        addToDatabaseTest();
        String url = "/rooms/getRoomNamesFromBuilding";
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithTVTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithCapacityTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithTvTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithWhiteBoardTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFilteredRoomsFromBuildingTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllFilteredRoomsTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void saveRoomTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void updateRoomTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteRoomTest() {
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testPutImage() throws Exception {
        addToDatabaseTest();
        String contentType = "image/jpeg";
        byte[] bytes = "image".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "orig.jpg", contentType, bytes);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/rooms/image/" + roomCode);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        mockMvc.perform(builder.file(file))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    String testGetImageUrl() throws Exception {
        testPutImage();

        return mockMvc.perform(RestDocumentationRequestBuilders.get("/rooms/image/getUrl/" + roomCode)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testDownloadImage() throws Exception {
        List<String> urls = parseLinkList(testGetImageUrl());
        for (String url: urls) {
            MockHttpServletResponse res = mockMvc.perform(RestDocumentationRequestBuilders.get(url))
                    .andExpect(status().isOk())
                    .andReturn().getResponse();
            byte[] response = res.getContentAsByteArray();

            assertArrayEquals(response, "image".getBytes());
            assertEquals(res.getContentType(), "image/jpeg");
        }

    }

    private List<String> parseLinkList(String list) {
        String[] link = list.replace("\"", "").replace("[", "").replace("]", "")
                .split(",");
        List<String> out = Arrays.asList(link);
        for (String s: out) {
            s = s.replace("[", "").replace("\"", "").replace("]", "");
            s = s.substring(16);
        }
        return out;
    }
}