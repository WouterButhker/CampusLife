package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;
import nl.tudelft.oopp.demo.CampusLife;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {
        CampusLife.class,
        SecurityConfiguration.class,
        PlatformTransactionManager.class
})
@Transactional
class RoomControllerTest {

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
    public void saveRoomTest() throws Exception {
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
        saveRoomTest();
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
        saveRoomTest();
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
        saveRoomTest();
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
        saveRoomTest();
        String url = "/rooms/getRoomNamesFromBuilding?building=123";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"TestRoom\"]"));
        String urlEmpty = "/rooms/getRoomNamesFromBuilding?building=0";
        mockMvc.perform(get(urlEmpty))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithRightsTest() throws Exception {
        saveRoomTest();
        String url = "/rooms/filter/rights?building=123&rights=2";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"roomCode\":\"69\",\"name\":\"TestRoom\",\""
                        + "capacity\":69,\"hasWhiteboard\":true,\"hasTV\":true,\"rights\":2,\""
                        + "building\":{\"buildingCode\":123,\"name\":\"Test Building\",\"location\""
                        + ":\"Somewhere\",\"openingHours\":\"11:11-22:22\",\"bikes\":42}}]"));
        String notRightsUrl = "/rooms/filter/rights?building=123&rights=0";
        mockMvc.perform(get(notRightsUrl))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithCapacityTest() throws Exception {
        saveRoomTest();
        String url = "/rooms/filter/getRoomsWithCapacity?building=123&lowerCapacity=0&"
                + "upperCapacity=69";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"roomCode\":\"69\",\"name\":\"TestRoom\",\""
                        + "capacity\":69,\"hasWhiteboard\":true,\"hasTV\":true,\"rights\":2,\""
                        + "building\":{\"buildingCode\":123,\"name\":\"Test Building\",\""
                        + "location\":\"Somewhere\",\"openingHours\":\"11:11-22:22\",\"bikes\""
                        + ":42}}]"));
        String notInCapacityUrl = "/rooms/filter/getRoomsWithCapacity?building=123&"
                + "lowerCapacity=100&upperCapacity=300";
        mockMvc.perform(get(notInCapacityUrl))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithTvTest() throws Exception {
        saveRoomTest();
        String url = "/rooms/filter/getRoomsWithTV?building=123";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"roomCode\":\"69\",\"name\":\"TestRoom\",\""
                        + "capacity\":69,\"hasWhiteboard\":true,\"hasTV\":true,\"rights\":2,\""
                        + "building\":{\"buildingCode\":123,\"name\":\"Test Building\",\"location\""
                        + ":\"Somewhere\",\"openingHours\":\"11:11-22:22\",\"bikes\":42}}]"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRoomsWithWhiteBoardTest() throws Exception {
        saveRoomTest();
        String url = "/rooms/filter/getRoomsWithWhiteBoard?building=123";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"roomCode\":\"69\",\"name\":\"TestRoom\",\""
                        + "capacity\":69,\"hasWhiteboard\":true,\"hasTV\":true,\"rights\":2,\""
                        + "building\":{\"buildingCode\":123,\"name\":\"Test Building\",\"location\""
                        + ":\"Somewhere\",\"openingHours\":\"11:11-22:22\",\"bikes\":42}}]"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFilteredRoomsFromBuildingTest() throws Exception {
        saveRoomTest();
        String url = "/rooms/filter/getFilteredRoomsFromBuilding?myBuilding=123&myRights=2"
                + "&hasTV=true&hasWhiteboard=true&minCap=0&maxCap=500";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"roomCode\":\"69\",\"name\":\"TestRoom\",\""
                        + "capacity\":69,\"hasWhiteboard\":true,\"hasTV\":true,\"rights\":2,\""
                        + "building\":{\"buildingCode\":123,\"name\":\"Test Building\",\"location\""
                        + ":\"Somewhere\",\"openingHours\":\"11:11-22:22\",\"bikes\":42}}]"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllFilteredRoomsTest() throws Exception {
        saveRoomTest();
        String url = "/rooms/filter/getAllFilteredRooms?myRights="
                + "2&hasTV=true&hasWhiteboard=true&minCap=0&maxCap=500";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"roomCode\":\"69\",\"name\":\"TestRoom\",\""
                        + "capacity\":69,\"hasWhiteboard\":true,\"hasTV\":true,\"rights\":2,\""
                        + "building\":{\"buildingCode\":123,\"name\":\"Test Building\",\"location\""
                        + ":\"Somewhere\",\"openingHours\":\"11:11-22:22\",\"bikes\":42}}]"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void saveRoomAlreadyExistTest() throws Exception {
        saveRoomTest();
        String url = "/rooms";
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(room)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void updateRoomTest() throws Exception {
        saveRoomTest();
        String url = "/rooms";
        room.setCapacity(333);
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(room)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void updateRoomNotExistTest() throws Exception {
        saveRoomTest();
        String url = "/rooms";
        room.setRoomCode("new updated fresh code");
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(room)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteRoomTest() throws Exception {
        saveRoomTest();
        String url = "/rooms/" + roomCode;
        mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("69"));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteRoomNotFoundTest() throws Exception {
        saveRoomTest();
        String url = "/rooms/" + 404;
        mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @WithMockUser(authorities = "Admin")
    void putImageTest() throws Exception {
        saveRoomTest();
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
    String getImageUrlTest() throws Exception {
        putImageTest();

        return mockMvc.perform(RestDocumentationRequestBuilders
                .get("/rooms/image/getUrl/" + roomCode))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getUrlWithoutImagesTest() throws Exception {
        saveRoomTest();
        String url = "/rooms/image/getUrl/" + roomCode;
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void downloadImageTest() throws Exception {
        List<String> urls = parseLinkListTest(getImageUrlTest());
        for (String url: urls) {
            MockHttpServletResponse res = mockMvc.perform(RestDocumentationRequestBuilders.get(url))
                    .andExpect(status().isOk())
                    .andReturn().getResponse();
            byte[] response = res.getContentAsByteArray();

            assertArrayEquals(response, "image".getBytes());
            assertEquals(res.getContentType(), "image/jpeg");
        }

    }

    private List<String> parseLinkListTest(String list) {
        String[] link = list.replace("\"", "").replace("[", "").replace("]", "")
                .split(",");
        List<String> out = Arrays.asList(link);
        for (String s: out) {
            s = s.replace("[", "").replace("\"", "").replace("]", "");
            s = s.substring(16);
        }
        return out;
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteImageTest() throws Exception {
        String url = "/rooms/image/2";
        mockMvc.perform(delete(url))
                .andExpect(status().isOk());
    }
}