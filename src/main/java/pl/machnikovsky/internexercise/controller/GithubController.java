package pl.machnikovsky.internexercise.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.coyote.Response;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.machnikovsky.internexercise.model.RepositoryEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GithubController {

    Gson gson = new Gson();


    @GetMapping(value = "/repos/{user}", produces = "application/json")
    public ResponseEntity getAllRepos(@PathVariable("user") String user) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("https://api.github.com/users")//  USERNAME/repos
                .build();
        ResponseEntity<String> recievedJson = restTemplate.getForEntity(String.format("/%s/repos", user), String.class);
        JsonObject[] jsonObjects = gson.fromJson(recievedJson.getBody(), JsonObject[].class);

        List<RepositoryEntity> repos = new ArrayList<>();


        for (JsonObject jsonObject : jsonObjects) {
            repos.add(new RepositoryEntity(jsonObject.get("name").toString(),
                    Integer.parseInt(jsonObject.get("stargazers_count").toString())));
        }

        return new ResponseEntity(repos ,HttpStatus.OK);
    }

    @GetMapping(value = "/stars/{user}", produces = "application/json")
    public ResponseEntity getRepoStars(@PathVariable("user") String user) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("https://api.github.com/users")//  USERNAME/repos
                .build();
        ResponseEntity<String> recievedJson = restTemplate.getForEntity(String.format("/%s/repos", user), String.class);
        JsonObject[] jsonObjects = gson.fromJson(recievedJson.getBody(), JsonObject[].class);


        int stars = 0;

        for (JsonObject jsonObject : jsonObjects) {
            stars += Integer.parseInt(jsonObject.get("stargazers_count").toString());
        }

        return new ResponseEntity(stars ,HttpStatus.OK);
    }

}
