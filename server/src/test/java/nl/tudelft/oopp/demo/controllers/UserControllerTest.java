package nl.tudelft.oopp.demo.controllers;

import com.google.gson.Gson;
import nl.tudelft.oopp.demo.DemoApplication;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {
        DemoApplication.class,
        SecurityConfiguration.class,
        PlatformTransactionManager.class
})
@Transactional
public class UserControllerTest {
    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    private User user = new User("Wouter", "1234", "Student");

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

    private void registerUser() throws Exception {
        user = new User("Wouter", "1234", "Student");
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user))).andExpect(status().isOk());
    }


    @Test
    @WithMockUser(authorities = "Admin")
    void testGetAll() throws Exception {
        //registerUser();
        mvc.perform(get("/rest/users/all")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Student")
    void testGetAllStudent() throws Exception {
        //registerUser();
        mvc.perform(get("/rest/users/all")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "Student")
    String testGetId() throws Exception {
        return mvc.perform(get("/rest/users/getId?username=Wouter")).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @WithMockUser(authorities = "Student")
    void testGetRole() throws Exception {
        mvc.perform(get("/rest/users/getRole?username=Wouter")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testChangeRole() throws Exception {
        mvc.perform(put("/rest/users/changeRole")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(user))).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testDelete() throws Exception {
        registerUser();
        int id = Integer.parseInt(testGetId());
        mvc.perform(delete("/rest/users/" + id)).andExpect(status().isOk());
    }

}
