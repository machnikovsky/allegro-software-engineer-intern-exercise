package pl.machnikovsky.internexercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.machnikovsky.internexercise.exception.GithubUserNotFoundException;
import pl.machnikovsky.internexercise.model.RepositoryEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GithubControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldReturnAllRepositories() throws Exception {
        String username = "machnikovsky";
        MvcResult mvcResult = mockMvc.perform(get("/repos/{username}", username))
                .andExpect(status().is(200))
                .andReturn();
        RepositoryEntity[] repos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RepositoryEntity[].class);

        assertEquals(11, repos.length);
    }

    @Test
    void shouldNotReturnAllRepositories() throws Exception {
        String username = "machnikovskx";
        String message = "User with a username " + username + " not found.";
        mockMvc.perform(get("/repos/{username}", username))
                .andExpect(status().is(404))
                .andExpect(res -> assertEquals(GithubUserNotFoundException.class, res.getResolvedException().getClass()))
                .andExpect(res -> assertEquals(message, res.getResolvedException().getMessage()));
    }


    @Test
    void shouldReturnUserStars() throws Exception {
        String username = "machnikovsky";
        MvcResult mvcResult = mockMvc.perform(get("/stars/{username}", username))
                .andExpect(status().is(200))
                .andReturn();
        int stars = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Integer.class);

        assertEquals(2, stars);
    }

    @Test
    void shouldNotReturnUserStars() throws Exception {
        String username = "machnikovskx";
        String message = "User with a username " + username + " not found.";
        mockMvc.perform(get("/stars/{username}", username))
                .andExpect(status().is(404))
                .andExpect(res -> assertEquals(GithubUserNotFoundException.class, res.getResolvedException().getClass()))
                .andExpect(res -> assertEquals(message, res.getResolvedException().getMessage()));
    }


}