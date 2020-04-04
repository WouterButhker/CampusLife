package nl.tudelft.oopp.demo.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import nl.tudelft.oopp.demo.DemoApplication;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.food.Food;
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
public class RestaurantControllerTest {

    private MockMvc mvc;

    private Integer id;
    private String name;
    private Building building;
    private String description;

    private Restaurant restaurant;
    private Food food;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    void init() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        id = 1234;
        name = "Testing";
        building = new Building(1, "Testing", "Testing street", "06:00-18:00, 06:00-18:00,"
                + " 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00", 45);
        description = "This is a restaurant created for testing.";
        insertBuildings();
    }

    private void insertBuildings() throws Exception {
        mvc.perform(post("/buildings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(building)));
        restaurant = new Restaurant(id, name, building, description);
    }

    private void postFood() throws Exception {
        food = new Food(1000, restaurant.getId(), "Name", 15.15);
        mvc.perform(post("/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(food)));
    }

    private void postRestaurant() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(restaurant)));
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void postRestaurantTest() throws Exception {
        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(restaurant)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllRestaurantsTest() throws Exception {
        postRestaurant();
        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void updateRestaurantTest() throws Exception {
        postRestaurant();
        String response = mvc.perform(get("/restaurants")).andReturn()
                .getResponse().getContentAsString();
        Type listType = new TypeToken<List<Restaurant>>() {}.getType();
        List<Restaurant> restaurantList = new Gson().fromJson(response, listType);
        Restaurant restaurant1 = restaurantList.get(0);
        restaurant1.setDescription("ala bala porto cala");
        mvc.perform(put("/restaurants/" + restaurant1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(restaurant1)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void updateRestaurantDoesntExistTest() throws Exception {
        mvc.perform(put("/restaurants/" + restaurant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(restaurant)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteRestaurantTest() throws Exception {
        postRestaurant();
        mvc.perform(get("/restaurants/delete?id=" + restaurant.getId()))
                .andExpect(status().isOk());
    }

    /*
    @WithMockUser(authorities = "Admin")
    @Test
    void getRestaurantsFromBuildingTest() throws Exception {
        postRestaurant();
        mvc.perform(get("/restaurants/getAllRestaurantsFromBuilding?building=" + building))
                .andExpect(status().isOk());
    }
    */

    @WithMockUser(authorities = "Admin")
    @Test
    void getRestaurantsIdAndNameTest() throws Exception {
        postRestaurant();
        postFood();
        mvc.perform(get("/restaurants/id+name"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities =  "Admin")
    @Test
    void getAllFoodTest() throws Exception {
        postRestaurant();
        postFood();
        mvc.perform(get("/restaurants/" + restaurant.getId() + "/food"))
                .andExpect(status().isOk());
    }

}
