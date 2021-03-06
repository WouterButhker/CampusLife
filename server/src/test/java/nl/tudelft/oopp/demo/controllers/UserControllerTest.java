package nl.tudelft.oopp.demo.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
        CampusLife.class,
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

    @Test
    @WithMockUser(authorities = "Admin")
    void testPutImage() throws Exception {
        registerUser();
        int id = Integer.parseInt(testGetId());
        String contentType = "image/jpeg";
        byte[] bytes = "image".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "orig.jpg", contentType, bytes);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/rest/users/image/" + id);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        mvc.perform(builder.file(file))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @WithMockUser(authorities = "Student")
    String testGetImageUrl() throws Exception {
        testPutImage();
        int id = Integer.parseInt(testGetId());
        return mvc.perform(get("/rest/users/image/getUrl/" + id)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @WithMockUser(authorities = "Student")
    void testDownloadImage() throws Exception {
        String url = testGetImageUrl().substring(16); // String without the http://localhost/
        MockHttpServletResponse res = mvc.perform(get(url)).andExpect(status().isOk())
                .andReturn().getResponse();
        byte[] response = res.getContentAsByteArray();

        assertArrayEquals(response, "image".getBytes());
        assertEquals(res.getContentType(), "image/jpeg");
    }

    private MockMultipartFile makeImageFile() {
        String contentType = "image/jpeg";
        byte[] bytes = "image".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "orig.jpg", contentType, bytes);
        return file;
    }

    private MockMultipartHttpServletRequestBuilder putImageToUser(int id) throws Exception {
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/rest/users/image/" + id);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });
        return builder;
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testPutImageWithoutUser() throws Exception {
        MockMultipartFile file = makeImageFile();
        MockMultipartHttpServletRequestBuilder builder = putImageToUser(1);
        mvc.perform(builder.file(file)).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "Admin")
    void testPutImageWithAnotherImage() throws Exception {
        registerUser();
        MockMultipartFile file = makeImageFile();
        MockMultipartHttpServletRequestBuilder builder =
                putImageToUser(Integer.parseInt(testGetId()));
        mvc.perform(builder.file(file));
        mvc.perform(builder.file(file)).andExpect(status().isOk());
    }


    @Test
    @WithMockUser(authorities = "Admin")
    void testGetImageUrlNoImage() throws Exception {
        registerUser();
        int id = Integer.parseInt(testGetId());
        assertNull(mvc.perform(get("/rest/users/image/getUrl/" + id))
                .andReturn().getResponse().getContentType());
    }

}
