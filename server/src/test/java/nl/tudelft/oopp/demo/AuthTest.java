package nl.tudelft.oopp.demo;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import nl.tudelft.oopp.demo.config.SecurityConfiguration;
import nl.tudelft.oopp.demo.entities.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {
        DemoApplication.class,
        SecurityConfiguration.class,
        PlatformTransactionManager.class
})
@Transactional
//@PropertySource("classpath:application.properties")
public class AuthTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    /**
     * runs a setup before every test.
     */
    @BeforeEach()
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    //@WithUserDetails(value = "admin", userDetailsServiceBeanName = "CustomUserDetailsService")
    @Test
    public void testLogin() throws Exception {
        mvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON).content("admin"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Student")
    @Test
    public void testGetBuildingsStudent() throws Exception {
        mvc.perform(get("/buildings/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBuildingsNotAUser() throws Exception {
        mvc.perform(get("/buildings/"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    public void testGetBuildingsForbidden() throws Exception {
        mvc.perform(get("/buildings/"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void addBuildingAdmin() throws Exception {
        // TODO fix to work with images
        /*
        Integer buildingCode = 99452;
        String name = "testBuilding";
        String location = "Delft";
        String openingHours = "11:00-13:00";
        Integer bikes = 30;
        Building building = new Building(buildingCode, name, location, openingHours, bikes);
        String url = "/buildings/";
        System.out.println();
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(building)))
                .andExpect(status().isOk());
         */
    }

    @WithMockUser(authorities = "Student")
    @Test
    public void addBuildingStudent() throws Exception {
        // TODO fix to work with images
        /*
        Integer buildingCode = 99452;
        String name = "testBuilding";
        String location = "Delft";
        String openingHours = "11:00-13:00";
        Integer bikes = 30;
        Building building = new Building(buildingCode, name, location, openingHours, bikes);
        String url = "/buildings/";
        mvc.perform(post(url, building)).andExpect(status().isForbidden());
         */
    }

}
