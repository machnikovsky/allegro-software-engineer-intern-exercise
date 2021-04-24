package pl.machnikovsky.internexercise.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.machnikovsky.internexercise.exception.GithubUserNotFoundException;
import pl.machnikovsky.internexercise.model.RepositoryEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GithubService {

    Gson gson;
    RestTemplate restTemplate;

    public GithubService() {
        gson = new Gson();
        restTemplate = new RestTemplateBuilder()
                .rootUri("https://api.github.com/users")
                .build();
    }

    public ResponseEntity<List<RepositoryEntity>> getAllRepos(String user, int page, int pageSize) {
        try {
            JsonObject[] jsonObjects = getJsonObjectsWithPagiantion(user, pageSize, page);

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

    public ResponseEntity<Integer> getRepoStars(String user) {
        try {
            List<JsonObject[]> jsonObjects = getJsonObjects(user);

            int stars = jsonObjects.stream()
                    .map(element -> (Arrays.stream(element)
                            .map(json -> Integer.parseInt(json.get("stargazers_count").toString()))
                            .reduce(0, Integer::sum)))
                    .reduce(0, Integer::sum);


            return new ResponseEntity(stars, HttpStatus.OK);
        } catch (RestClientException e) {
            throw new GithubUserNotFoundException(user);
        }
    }


    public ResponseEntity<List<RepositoryEntity>> getAllReposAtOnePage(String user) {
        try {
            List<JsonObject[]> jsonObjectsList = getJsonObjects(user);

            List<RepositoryEntity> repos = new ArrayList<>();
            for (JsonObject[] jsonObjects : jsonObjectsList) {
                for (JsonObject jsonObject : jsonObjects) {
                    repos.add(new RepositoryEntity(jsonObject.get("name").toString().replaceAll("\"", ""),
                            Integer.parseInt(jsonObject.get("stargazers_count").toString())));
                }
            }
            return new ResponseEntity(repos, HttpStatus.OK);
        } catch (RestClientException e) {
            throw new GithubUserNotFoundException(user);
        }
    }

    private List<JsonObject[]> getJsonObjects(String user) {
        ResponseEntity<String> recievedUserJson = restTemplate.getForEntity(String.format("/%s", user), String.class);
        JsonObject userJson = gson.fromJson(recievedUserJson.getBody(), JsonObject.class);
        int repositoryCount = userJson.get("public_repos").getAsInt();
        int pages = (repositoryCount / 100) + 1;
        List<JsonObject[]> jsonObjects = new ArrayList<>();
        ResponseEntity<String> recievedJson;
        for (int i = 1; i <= pages; i++) {
            recievedJson = restTemplate.getForEntity(String
                    .format("/%s/repos?per_page=%d&page=%d", user, 100, i), String.class);
            jsonObjects.add(gson.fromJson(recievedJson.getBody(), JsonObject[].class));
        }
        return jsonObjects;
    }


    private JsonObject[] getJsonObjectsWithPagiantion(String user, int pageSize, int page) {
        ResponseEntity<String> entity = restTemplate.getForEntity(String
                .format("/%s/repos?per_page=%d&page=%d", user, pageSize, page), String.class);
        return gson.fromJson(entity.getBody(), JsonObject[].class);
    }



}
