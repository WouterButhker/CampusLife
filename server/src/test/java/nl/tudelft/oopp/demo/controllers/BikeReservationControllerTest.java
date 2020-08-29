package nl.tudelft.oopp.demo.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import nl.tudelft.oopp.demo.CampusLife;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.BikeReservation;
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
        CampusLife.class,
        SecurityConfiguration.class,
        PlatformTransactionManager.class
})

@Transactional
public class BikeReservationControllerTest {

    private MockMvc mvc;

    private User user;
    private Building pickUpBuilding;
    private Building dropOffBuilding;
    private String date;
    private String timeSlot;

    private BikeReservation bikeReservation;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    void init() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        user = new User("test", "test");
        pickUpBuilding = new Building(1, "Test", "Test street", "06:00-18:00, 06:00-18:00,"
                + " 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00", 45);

        dropOffBuilding = new Building(2, "Test2", "Test street2", "06:00-18:00, 06:00-18:00, "
                + "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00", 32);

        date = "22/03/2020";
        timeSlot = "14:00-19:00";
        insertBuildings();
    }

    private void insertBuildings() throws Exception {
        mvc.perform(post("/buildings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(pickUpBuilding)));
        mvc.perform(post("/buildings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(dropOffBuilding)));
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user)));
        int userId = Integer.parseInt(mvc.perform(get("/rest/users/getId?username=test"))
                .andReturn().getResponse().getContentAsString());
        user = new User(userId);
        bikeReservation = new BikeReservation(user, pickUpBuilding,
                dropOffBuilding, date, timeSlot);
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllBikeReservationsTest() throws Exception {
        mvc.perform(get("/bikeReservations/all"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void updateBikeReservationButNotThere() throws Exception {
        mvc.perform(put("/bikeReservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(bikeReservation)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void updateBikeReservation() throws Exception {
        bikeReservation.setId(3);
        mvc.perform(post("/bikeReservations/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(bikeReservation)));
        String response = mvc.perform(get("/bikeReservations/all")).andReturn()
                        .getResponse().getContentAsString();
        Type listType = new TypeToken<List<BikeReservation>>() {}.getType();
        List<BikeReservation> bikeReservations = new Gson().fromJson(response, listType);
        BikeReservation bikeReservationChanged = bikeReservations.get(0);
        bikeReservationChanged.setDate("12/12/2012");
        mvc.perform(put("/bikeReservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(bikeReservationChanged))).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void createBikeReservation() throws Exception {
        bikeReservation.setId(4);
        mvc.perform(post("/bikeReservations/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(bikeReservation))).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteBikeReservationButNotThere() throws Exception {
        mvc.perform(delete("/bikeReservations/5"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteBikeReservationTest() throws Exception {
        bikeReservation.setId(2);
        mvc.perform(post("/bikeReservations/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(bikeReservation)));
        String response = mvc.perform(get("/bikeReservations/all")).andReturn()
                .getResponse().getContentAsString();
        Type listType = new TypeToken<List<BikeReservation>>() {}.getType();
        List<BikeReservation> bikeReservations = new Gson().fromJson(response, listType);
        BikeReservation bikeReservationInserted = bikeReservations.get(0);
        int id = bikeReservationInserted.getId();
        String url = "/bikeReservations/" + id;
        mvc.perform(delete(url))
                .andExpect(status().isOk());
    }
}
