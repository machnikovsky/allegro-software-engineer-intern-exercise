package pl.machnikovsky.internexercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.machnikovsky.internexercise.exception.GithubUserNotFoundEntity;
import pl.machnikovsky.internexercise.model.RepositoryEntity;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturnAllRepositories() {
        String URLprefix = "http://localhost:" + port;
        String username = "machnikovsky";
        ResponseEntity<RepositoryEntity[]> responseEntity = testRestTemplate.exchange(
                URLprefix + "/repos/" + username,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                RepositoryEntity[].class
        );

        Assertions.assertEquals(11, responseEntity.getBody().length);
    }

    @Test
    void shouldNotReturnAllRepositories() {
        String URLprefix = "http://localhost:" + port;
        String username = "machnikovskx";

        ResponseEntity<GithubUserNotFoundEntity> responseEntity = testRestTemplate.exchange(
                URLprefix + "/repos/" + username,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                GithubUserNotFoundEntity.class
        );

        String message = "User with a username " + username + " not found.";

        Assertions.assertEquals(GithubUserNotFoundEntity.class, responseEntity.getBody().getClass());
        Assertions.assertEquals(message, responseEntity.getBody().getMessage());
    }

    @Test
    public void shouldReturnUserStars() {
        String URLprefix = "http://localhost:" + port;
        String username = "machnikovsky";
        ResponseEntity<Integer> responseEntity = testRestTemplate.exchange(
                URLprefix + "/stars/" + username,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Integer.class
        );

        Assertions.assertEquals(2, responseEntity.getBody());
    }

    @Test
    void shouldNotReturnUserStars() {
        String URLprefix = "http://localhost:" + port;
        String username = "machnikovskx";

        ResponseEntity<GithubUserNotFoundEntity> responseEntity = testRestTemplate.exchange(
                URLprefix + "/stars/" + username,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                GithubUserNotFoundEntity.class
        );

        String message = "User with a username " + username + " not found.";

        Assertions.assertEquals(GithubUserNotFoundEntity.class, responseEntity.getBody().getClass());
        Assertions.assertEquals(message, responseEntity.getBody().getMessage());
    }
}
