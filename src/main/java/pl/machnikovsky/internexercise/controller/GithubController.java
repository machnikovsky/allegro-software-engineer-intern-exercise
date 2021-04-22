package pl.machnikovsky.internexercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.apache.coyote.Response;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import pl.machnikovsky.internexercise.exception.GithubUserNotFoundException;
import pl.machnikovsky.internexercise.model.RepositoryEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GithubController {

    Gson gson;

    public GithubController() {
        this.gson = new Gson();
    }

    @GetMapping(value = "/repos/{user}")
    public ResponseEntity<Object> getAllRepos(@PathVariable("user") String user) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("https://api.github.com/users")//  USERNAME/repos
                .build();

        try {
            ResponseEntity<String> recievedJson = restTemplate.getForEntity(String.format("/%s/repos", user), String.class);
            JsonObject[] jsonObjects = gson.fromJson(recievedJson.getBody(), JsonObject[].class);
            List<RepositoryEntity> repos = new ArrayList<>();
            for (JsonObject jsonObject : jsonObjects) {
                repos.add(new RepositoryEntity(jsonObject.get("name").toString().replaceAll("\"", ""),
                        Integer.parseInt(jsonObject.get("stargazers_count").toString())));
            }
            return new ResponseEntity(repos, HttpStatus.OK);
        } catch (RestClientException e) {
            throw new GithubUserNotFoundException(user);
        }
    }

    @GetMapping(value = "/stars/{user}", produces = "application/json")
    public ResponseEntity<List<RepositoryEntity>> getRepoStars(@PathVariable("user") String user) {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("https://api.github.com/users")//  USERNAME/repos
                .build();

        try {
            ResponseEntity<String> recievedJson = restTemplate.getForEntity(String.format("/%s/repos", user), String.class);
            JsonObject[] jsonObjects = gson.fromJson(recievedJson.getBody(), JsonObject[].class);

            int stars = 0;
            for (JsonObject jsonObject : jsonObjects) {
                stars += Integer.parseInt(jsonObject.get("stargazers_count").toString());
            }
            return new ResponseEntity(stars, HttpStatus.OK);
        } catch (RestClientException e) {
            throw new GithubUserNotFoundException(user);
        }
    }

}
