package nl.tudelft.oopp.demo.controllers;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import nl.tudelft.oopp.demo.CampusLife;
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


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {
        CampusLife.class,
        SecurityConfiguration.class,
        PlatformTransactionManager.class
})
@Transactional
public class LoginControllerTest {
    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    public User user = new User("Wouter", "1234", "Student");

    /**
     * runs a setup before every test.
     */
    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser
    void registerUser() throws Exception {
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user))).andExpect(status().isOk());
    }

    @Test
    void testRegisterUser() throws Exception {
        registerUser();
    }

    @Test
    void testLogin() throws Exception {
        registerUser();
        mvc.perform(get("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("Wouter")).andExpect(status().isOk());

    }
}
