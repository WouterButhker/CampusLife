package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.DemoApplication;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {
        DemoApplication.class,
        SecurityConfiguration.class,
        PlatformTransactionManager.class
})
@Transactional
class FavoriteRestaurantControllerTest {

    @BeforeEach
    void setUpper() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void addFavoriteRestaurantTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFavoriteRestaurantsTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteFavoriteRestaurantTest() {
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFavoriteRestaurantTest() {
    }
}