package nl.tudelft.oopp.demo.controllers;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
                .content(new Gson().toJson(building)));
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

}
