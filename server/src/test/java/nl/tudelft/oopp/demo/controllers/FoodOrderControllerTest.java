package nl.tudelft.oopp.demo.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.demo.DemoApplication;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.food.Food;
import nl.tudelft.oopp.demo.entities.food.FoodOrder;
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
public class FoodOrderControllerTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    private User user;
    private Restaurant restaurant;
    private String date;
    private String timeslot;
    private FoodOrder foodOrder;
    private Building building;
    private Food food;

    /**
     * runs a setup before every test.
     */
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        user = new User("test", "test");
        building = new Building(1, "Test", "Test street", "06:00-18:00, 06:00-18:00,"
                + " 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00", 45);
        restaurant = new Restaurant(1, "Tosti", building, "Lekkere tosti");
        date = "22/03/2020";
        timeslot = "14:00-19:00";
        food = new Food(1, restaurant.getId(), "Tosti", 2.15);

    }

    private void setupDatabase() throws Exception {
        mvc.perform(post("/buildings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(building)));
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(user)));
        food = new Gson().fromJson(mvc.perform(post("/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(food)))
                .andReturn().getResponse().getContentAsString(), Food.class);
        int userId = Integer.parseInt(mvc.perform(get("/rest/users/getId?username=test"))
                .andReturn().getResponse().getContentAsString());
        user = new User(userId);
        restaurant = postRestaurant();
    }

    private Restaurant postRestaurant() throws Exception {
        String response = mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(restaurant)))
                .andReturn().getResponse().getContentAsString();
        Restaurant responseRestaurant = new Gson().fromJson(response, Restaurant.class);
        restaurant.setId(responseRestaurant.getId());
        return responseRestaurant;
    }

    @WithMockUser(authorities = "Admin")
    @Test
    FoodOrder addFoodOrderTest() throws Exception {
        setupDatabase();
        foodOrder = new FoodOrder(user, date, timeslot, restaurant);
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        ArrayList<Integer> foodQuantity = new ArrayList<>();
        int foodId = food.getId();
        int quantity = 2;
        foodQuantity.add(foodId);
        foodQuantity.add(quantity);
        list.add(foodQuantity);
        foodOrder.setFoodsList(list);
        String response = mvc.perform(post("/foodOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(foodOrder)))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        return new Gson().fromJson(response, FoodOrder.class);
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFoodOrderTest() throws Exception {
        addFoodOrderTest();
        mvc.perform(get("/foodOrder/user/" + user.getId())).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getFoodsTest() throws Exception {
        int id = addFoodOrderTest().getId();
        mvc.perform(get("/foodOrder/" + id)).andExpect(status().isOk());
    }
}
