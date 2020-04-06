package nl.tudelft.oopp.demo.controllers;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import nl.tudelft.oopp.demo.DemoApplication;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
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
public class FoodControllerTest {

    private MockMvc mvc;

    private Integer id;
    private Integer restaurantId;
    private String name;
    private Double price;

    private Food food;

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    void init() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        id = 0; // The id is not saved with this value, it is randomly generated.
        name = "Testing";
        restaurantId = 1;
        price = 10.30;
        food = new Food(id, restaurantId, name, price);
    }

    private void postFood() throws Exception {
        String response = mvc.perform(post("/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(food)))
                .andReturn().getResponse().getContentAsString();
        Food responseFood = new Gson().fromJson(response, Food.class);
        food.setId(responseFood.getId());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void postRestaurantTest() throws Exception {
        mvc.perform(post("/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(food)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteFoodTest() throws Exception {
        postFood();
        mvc.perform(delete("/foods/" + food.getId()))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteFoodDoesntExistTest() throws Exception {
        mvc.perform(delete("/foods/" + food.getId()))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void updateFoodTest() throws Exception {
        postFood();
        String response = mvc.perform(get("/foods")).andReturn()
                .getResponse().getContentAsString();
        Type listType = new TypeToken<List<Food>>() {}.getType();
        List<Food> foodsList = new Gson().fromJson(response, listType);
        Food food2 = foodsList.get(0);
        food2.setPrice(5.50);
        mvc.perform(put("/foods/" + food2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(food2)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void updateFoodDoesntExistTest() throws Exception {
        mvc.perform(put("/foods/" + food.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(food)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void getAllFoodsTest() throws Exception {
        postFood();
        mvc.perform(get("/foods"))
                .andExpect(status().isOk());
    }

}
