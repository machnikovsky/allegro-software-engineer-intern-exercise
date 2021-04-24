package pl.machnikovsky.internexercise.controller;

import com.google.gson.JsonObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.machnikovsky.internexercise.exception.GithubUserNotFoundException;
import pl.machnikovsky.internexercise.model.RepositoryEntity;
import pl.machnikovsky.internexercise.service.GithubService;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GithubController {

    Gson gson;
    GithubService githubService;

    public GithubController(GithubService githubService) {
        this.gson = new Gson();
        this.githubService = githubService;
    }

    @GetMapping(value = "/repos/{user}")
    public ResponseEntity<List<RepositoryEntity>> getAllRepos(@PathVariable("user") String user,
                                                              @RequestParam(value = "page", defaultValue = "1") int page,
                                                              @RequestParam(value = "page_size", defaultValue = "100") int pageSize) {
        return githubService.getAllRepos(user, page, pageSize);
    }

    @GetMapping(value = "/stars/{user}", produces = "application/json")
    public ResponseEntity<Integer> getRepoStars(@PathVariable("user") String user) {
        return githubService.getRepoStars(user);
    }

    @GetMapping(value = "/repos/{user}/all")
    public ResponseEntity<List<RepositoryEntity>> getAllReposAtOnePage(@PathVariable("user") String user) {
        return githubService.getAllReposAtOnePage(user);
    }

}
