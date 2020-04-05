package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import nl.tudelft.oopp.demo.entities.Building;
import nl.tudelft.oopp.demo.entities.Restaurant;
import nl.tudelft.oopp.demo.entities.food.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
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
        id = 0; // The id is not saved with this value, it is randomly generated.
        name = "Testing";
        building = new Building(1, "Testing", "Testing street", "06:00-18:00, 06:00-18:00,"
                + " 06:00-18:00, 06:00-18:00, 06:00-18:00, 06:00-18:00, 19:00-21:00", 45);
        description = "This is a restaurant created for testing.";
        insertBuildings();
        restaurant = new Restaurant(id, name, building, description);
    }

    private Building insertBuildings() throws Exception {
        String response = mvc.perform(post("/buildings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(building)))
                .andReturn().getResponse().getContentAsString();
        Building buildingResponse = new Gson().fromJson(response, Building.class);
        return buildingResponse;
    }

    private void postFood() throws Exception {
        food = new Food(1000, restaurant.getId(), "Name", 15.15);
        mvc.perform(post("/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(food)));
    }

    private Restaurant postRestaurant() throws Exception {
        String response = mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(restaurant)))
                .andReturn().getResponse().getContentAsString();
        Restaurant responseRestaurant = new Gson().fromJson(response, Restaurant.class);
        restaurant.setId(responseRestaurant.getId());
        return responseRestaurant;
        /*String response = mvc.perform(get("/restaurants")).andReturn()
                .getResponse().getContentAsString();
        Type listType = new TypeToken<List<Restaurant>>() {}.getType();
        List<Restaurant> restaurantList = new Gson().fromJson(response, listType);
        Restaurant responseRestaurant = restaurantList.get(0);
        return responseRestaurant;*/
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void postRestaurantTest() throws Exception {
        insertBuildings();
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

    @WithMockUser(authorities = "Admin")
    @Test
    void getRestaurantsFromBuildingTest() throws Exception {
        postRestaurant();
        mvc.perform(get("/restaurants/getAllRestaurantsFromBuilding?building="
                + building.getBuildingCode()))
                .andExpect(status().isOk());
    }

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


    @Test
    @WithMockUser(authorities = "Admin")
    void testPutImage() throws Exception {
        int id = postRestaurant().getId();

        String contentType = "image/jpeg";
        byte[] bytes = "image".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "orig.jpg", contentType, bytes);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/restaurants/image/" + id);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        mvc.perform(builder.file(file))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testGetImageUrl() throws Exception {
        testPutImage();
        int id = restaurant.getId();
        String out = mvc.perform(get("/restaurants/image/getUrl/" + id))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(out);
        System.out.println("---------------------------"
                + "--------------------------------------\n\n\n\n\n\n");
        //return out;
    }

    void putImage() throws Exception {
        int id = postRestaurant().getId();

        String contentType = "image/jpeg";
        byte[] bytes = "image".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "orig.jpg", contentType, bytes);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/restaurants/image/" + id);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        mvc.perform(builder.file(file));
    }

    String getImageUrl() throws Exception {
        putImage();
        int id = restaurant.getId();
        String response = mvc.perform(get("/restaurants/image/getUrl/" + id))
                .andReturn().getResponse().getContentAsString();
        Type listType = new TypeToken<List<String>>() {}.getType();
        List<String> outList = new Gson().fromJson(response, listType);
        String out = outList.get(0);
        return out;
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testDownloadImage() throws Exception {
        String url = getImageUrl().substring(16); // String without the http://localhost/
        MockHttpServletResponse res = mvc.perform(get(url)).andExpect(status().isOk())
                .andReturn().getResponse();
        byte[] response = res.getContentAsByteArray();

        assertArrayEquals(response, "image".getBytes());
        assertEquals(res.getContentType(), "image/jpeg");
    }

    @WithMockUser(authorities = "Admin")
    @Test
    void deleteImageTest() throws Exception {
        String url = "/restaurants/image/1";
        mvc.perform(delete(url))
                .andExpect(status().isOk());
    }


}
