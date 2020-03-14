package nl.tudelft.oopp.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    @BeforeEach()
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void testLogin() throws Exception {
        mvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON).content("admin"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Student")
    @Test
    public void testGetBuildingsStudent() throws Exception {
        mvc.perform(get("/buildings/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBuildingsNotAUser() throws Exception {
        mvc.perform(get("/buildings/all"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser
    @Test
    public void testGetBuildingsForbidden() throws Exception {
        mvc.perform(get("/buildings/all"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "Admin")
    @Test
    public void addBuildingAdmin() throws Exception {
        String buildingCode = "99452";
        String name = "testBuilding";
        String location = "Delft";
        String openingHours = "11:00-13:00";
        String bikesString = "30";
        String url = "/buildings/add?buildingCode=" + buildingCode
                + "&name=" + name + "&location=" + location + "&openingHours=" + openingHours
                + "&bikes=" + bikesString;
        mvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "Student")
    @Test
    public void addBuildingStudent() throws Exception {
        String buildingCode = "99452";
        String name = "testBuilding";
        String location = "Delft";
        String openingHours = "11:00-13:00";
        String bikesString = "30";
        String url = "/buildings/add?buildingCode=" + buildingCode
                + "&name=" + name + "&location=" + location + "&openingHours=" + openingHours
                + "&bikes=" + bikesString;
        mvc.perform(get(url))
                .andExpect(status().isForbidden());
    }

}
