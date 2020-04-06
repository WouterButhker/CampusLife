package nl.tudelft.oopp.demo.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import nl.tudelft.oopp.demo.DemoApplication;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.reservation.PersonalReservation;
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
public class PersonalReservationControllerTest {
    private MockMvc mvc;

    private User user;
    private String activity;
    private String date;
    private String timeSlot;

    private PersonalReservation personalReservation;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    void init() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        user = new User("test", "test");
        activity = "test description";

        date = "22/03/2020";
        timeSlot = "14:00-19:00";
        insertReservation();
    }

    private void insertReservation() throws Exception {
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user)));
        int userId = Integer.parseInt(mvc.perform(get("/rest/users/getId?username=test"))
                .andReturn().getResponse().getContentAsString());
        user = new User(userId);
        personalReservation = new PersonalReservation(user,
                date, timeSlot, activity);
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllPersonalReservationsTest() throws Exception {
        mvc.perform(get("/personalReservations/all"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void updatePersonalReservationButNotThere() throws Exception {
        mvc.perform(put("/personalReservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(personalReservation)))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void getAllMyReservationsTest() throws Exception {
        Integer userId = 1;
        mvc.perform(get("/personalReservations/myReservations?user=" + userId))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void createPersonalReservation() throws Exception {
        personalReservation.setId(4);
        mvc.perform(post("/personalReservations/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(personalReservation))).andExpect(status().isCreated());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deletePersonalReservationButNotThere() throws Exception {
        mvc.perform(delete("/personalReservations/5"))
                .andExpect(status().isNotFound());
    }

}
