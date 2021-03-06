package nl.tudelft.oopp.demo.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nl.tudelft.oopp.demo.CampusLife;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class RoomReservationTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

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

    @WithMockUser(authorities = "Student")
    @Test
    public void getAllReservationsTest() throws Exception {
        mvc.perform(get("/roomReservations/all"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void getAllUserReservationsTest() throws Exception {
        Integer userId = 1;
        mvc.perform(get("/roomReservations/allForUser?user=" + userId))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void getAllRoomReservationsTest() throws Exception {
        String roomId = "abc";
        mvc.perform(get("/roomReservations/allForRoom?room=" + roomId))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void addRoomReservationTest() throws Exception {
        Integer userId = 1;
        String roomId = "abc";
        String slot = "00:0fjhashjdehawejhasdfadfasd0";

        String url = "/roomReservations/add?user=" + userId
                + "&room=" + roomId + "&slot=" + slot;
        mvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void getAllMyReservationsTest() throws Exception {
        Integer userId = 1;
        mvc.perform(get("/roomReservations/myReservations?user=" + userId))
                .andExpect(status().isOk());
    }

}
