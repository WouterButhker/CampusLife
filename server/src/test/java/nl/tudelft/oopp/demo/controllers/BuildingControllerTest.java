package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import nl.tudelft.oopp.demo.DemoApplication;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import nl.tudelft.oopp.demo.entities.Building;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
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
        DemoApplication.class,
        SecurityConfiguration.class,
        PlatformTransactionManager.class
})

@Transactional
public class BuildingControllerTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    TestInfo testInfo;

    Integer buildingCode;
    String name;
    String location;
    String openingHours;
    Integer bikes;
    Building building;

    /**
     * runs a setup before every test.
     */
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    private void hasOneBuilding() {
        buildingCode = 99452;
        name = "testBuilding";
        location = "Delft";
        openingHours = "11:00-13:00";
        bikes = 30;
        building = new Building(buildingCode, name, location, openingHours, bikes);
    }

    private void postOneBuilding() throws Exception {
        mvc.perform(post("/buildings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(building))).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Student")
    @Test
    public void getBuildingsWithBikesTest() throws Exception {
        mvc.perform(get("/buildings/bikes"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void addBuildingAlreadyExistsTest() throws Exception {
        hasOneBuilding();
        postOneBuilding();
        String url = "/buildings/";
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(building)))
                .andExpect(status().isBadRequest());

    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void updateBuildingTest() throws Exception {
        hasOneBuilding();
        postOneBuilding();
        String url = "/buildings/";
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(building)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void updateOneBuildingNotExistsTest() throws Exception {
        hasOneBuilding();
        String url = "/buildings/";
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(building)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void getBuildingByCodeTest() throws Exception {
        hasOneBuilding();
        postOneBuilding();
        String url = "/buildings/" + buildingCode;
        mvc.perform(get(url)).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void deleteBuildingTest() throws Exception {
        hasOneBuilding();
        postOneBuilding();
        String url = "/buildings/" + buildingCode;
        mvc.perform(delete(url)).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void deleteBuildingNotExistingTest() throws Exception {
        hasOneBuilding();
        postOneBuilding();
        buildingCode = -buildingCode;
        String url = "/buildings/" + buildingCode;
        mvc.perform(delete(url)).andExpect(status().isNotFound());
    }

    private MockMultipartFile makeImageFile() {
        String contentType = "image/jpeg";
        byte[] bytes = "image".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "orig.jpg", contentType, bytes);
        return file;
    }

    private MockMultipartHttpServletRequestBuilder putImageToBuilding() {
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/buildings/image/" + buildingCode);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });
        return builder;
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testPutImage() throws Exception {
        hasOneBuilding();
        postOneBuilding();
        MockMultipartFile file = makeImageFile();
        MockMultipartHttpServletRequestBuilder builder = putImageToBuilding();
        mvc.perform(builder.file(file)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testPutImageWithoutBuilding() throws Exception {
        hasOneBuilding();
        MockMultipartFile file = makeImageFile();
        MockMultipartHttpServletRequestBuilder builder = putImageToBuilding();
        mvc.perform(builder.file(file)).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testPutImageWithAnotherImage() throws Exception {
        hasOneBuilding();
        postOneBuilding();
        MockMultipartFile file = makeImageFile();
        MockMultipartHttpServletRequestBuilder builder = putImageToBuilding();
        mvc.perform(builder.file(file));
        mvc.perform(builder.file(file)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Student")
    String testGetImageUrl() throws Exception {
        testPutImage();
        int id = buildingCode;
        return mvc.perform(get("/buildings/image/getUrl/" + id)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testGetImageUrlNoImage() throws Exception {
        hasOneBuilding();
        postOneBuilding();
        int id = buildingCode;
        assertNull(mvc.perform(get("/buildings/image/getUrl/" + id))
                .andReturn().getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testDownloadImage() throws Exception {
        String url = testGetImageUrl().substring(16); // String without the http://localhost/
        MockHttpServletResponse res = mvc.perform(get(url)).andExpect(status().isOk())
                .andReturn().getResponse();
        byte[] response = res.getContentAsByteArray();

        assertArrayEquals(response, "image".getBytes());
        assertEquals(res.getContentType(), "image/jpeg");
    }

    @Test
    void testImageControllerCheckFile() {
        String contentType = "image/jpeg";
        byte[] bytes = "image".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "../file", "../orig.jpg", contentType, bytes);

        assertThrows(IllegalArgumentException.class, () -> {
            ImageController.checkFile(file);
        });
    }

}
