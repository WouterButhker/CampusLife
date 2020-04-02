package nl.tudelft.oopp.demo.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import nl.tudelft.oopp.demo.DemoApplication;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.BikeReservation;
import nl.tudelft.oopp.demo.repositories.BikeReservationRepository;
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
        user = new User(6);
        pickUpBuilding = new Building(1, "Test", "Test street", "06:00-18:00, 06:00-18:00,"
                + " 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00", 45);

        dropOffBuilding = new Building(2, "Test2", "Test street2", "06:00-18:00, 06:00-18:00, "
                + "06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00", 32);

        date = "22/03/2020";
        timeSlot = "14:00-19:00";
        bikeReservation = new BikeReservation(user, pickUpBuilding, dropOffBuilding, date, timeSlot);
    }

//    @WithMockUser(authorities = "Admin")
//    @BeforeEach
//    void insertBuildings() throws Exception {
//        mvc.perform(post("/buildings/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new Gson().toJson(pickUpBuilding)))
//                .andExpect(status().isOk());
//        mvc.perform(post("/buildings/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new Gson().toJson(dropOffBuilding)))
//                .andExpect(status().isOk());
//    }

    @WithMockUser(authorities = "Student")
    @Test
    void getAllBikeReservationsTest() throws Exception {
        mvc.perform(get("/bikeReservations/all"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Student")
    @Test
    void updateBikeReservationButNotThere() throws Exception {
        mvc.perform(put("/bikeReservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(bikeReservation)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(authorities = "Student")
    @Test
    void createBikeReservation() throws Exception {
        mvc.perform(post("/bikeReservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(bikeReservation)))
            .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Student")
    @Test
    void deleteBikeReservationButNotThere() throws Exception {
        mvc.perform(delete("/bikeReservations/5"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(authorities = "Student")
    @Test
    void deleteBikeReservationTest() throws Exception {
        mvc.perform(post("/bikeReservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(bikeReservation)))
                .andExpect(status().isOk());
        mvc.perform(delete("/bikeReservations/5"))
                .andExpect(status().isOk());
    }
}
