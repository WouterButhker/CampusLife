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
import nl.tudelft.oopp.demo.entities.*;
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
class FavoriteRestaurantControllerTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    private Building building;
    private Integer id;
    private Restaurant restaurant;
    private User user;
    private FavoriteRestaurant favoriteRestaurant;

    @BeforeEach
    void setUpper() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        building = new Building(55, "Test building", "here street", "10:00-21:00", 3);
        id = 42;
        restaurant = new Restaurant(12, "name", building, "Food Here");
        user = new User("test", "test");
        favoriteRestaurant = new FavoriteRestaurant(id, restaurant, user);
    }

    @WithMockUser(authorities = "Admin")
    public void saveUserAndRestaurant() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user)));
        mockMvc.perform(post("/buildings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(building)));
        mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(restaurant)));

        String responseUserString = mockMvc.perform(get("/rest/users/all"))
                .andReturn().getResponse().getContentAsString();
        Gson gsonUser = new Gson();
        Type listTypeUser = new TypeToken<List<User>>() {
        }.getType();
        List<User> userList = gsonUser.fromJson(responseUserString, listTypeUser);
        user = userList.get(0);

        String responseBuildingString = mockMvc.perform(get("/buildings"))
                .andReturn().getResponse().getContentAsString();
        Gson gsonBuilding = new Gson();
        Type listTypeBuilding = new TypeToken<List<Building>>() {
        }.getType();
        List<Building> buildingsList = gsonBuilding.fromJson(responseBuildingString,
                listTypeBuilding);
        building = buildingsList.get(0);

        String responseRestaurantString = mockMvc.perform(get("/restaurants"))
                .andReturn().getResponse().getContentAsString();
        Gson gsonRestaurant = new Gson();
        Type listTypeRestaurant = new TypeToken<List<Restaurant>>() {
        }.getType();
        List<Restaurant> restaurantsList = gsonRestaurant.fromJson(responseRestaurantString,
                listTypeRestaurant);
        restaurant = restaurantsList.get(0);

        favoriteRestaurant = new FavoriteRestaurant(id, restaurant, user);
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void addFavoriteRestaurantTest() throws Exception {
        saveUserAndRestaurant();
        mockMvc.perform(post("/favoriterestaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(favoriteRestaurant)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void addFavoriteRestaurantFoundTest() throws Exception {
        addFavoriteRestaurantTest();
        mockMvc.perform(post("/favoriterestaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(favoriteRestaurant)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFavoriteRestaurantsTest() throws Exception {
        addFavoriteRestaurantTest();
        String url = "/favoriterestaurants/user/" + user.getId();
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteFavoriteRestaurantNotFoundTest() throws Exception {
        addFavoriteRestaurantTest();
        String url = "/favoriterestaurants/404";
        mockMvc.perform(delete(url))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteFavoriteRestaurantTest() throws Exception {
        addFavoriteRestaurantTest();
        String requestUrl = "/favoriterestaurants/user/" + user.getId();
        String responseString = mockMvc.perform(get(requestUrl))
                .andReturn().getResponse().getContentAsString();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<FavoriteRestaurant>>() {
        }.getType();
        List<FavoriteRestaurant> favoriteRestaurantList = gson.fromJson(responseString, listType);
        favoriteRestaurant = favoriteRestaurantList.get(0);

        String url = "/favoriterestaurants/" + favoriteRestaurant.getId();
        mockMvc.perform(delete(url))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFavoriteRestaurantTest() throws Exception {
        addFavoriteRestaurantTest();
        String url = "/favoriterestaurants/user/" + user.getId() + "/restaurant/"
                + restaurant.getId();
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }
}