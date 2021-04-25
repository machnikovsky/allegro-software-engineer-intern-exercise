package pl.machnikovsky.internexercise.controller;

import org.springframework.http.ResponseEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import pl.machnikovsky.internexercise.model.RepositoryEntity;
import pl.machnikovsky.internexercise.service.GithubService;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://www.zajavva.pl")
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
    public ResponseEntity<Integer> getUserStars(@PathVariable("user") String user) {
        return githubService.getUserStars(user);
    }

    @GetMapping(value = "/repos/{user}/all")
    public ResponseEntity<List<RepositoryEntity>> getAllReposAtOnePage(@PathVariable("user") String user) {
        return githubService.getAllReposAtOnePage(user);
    }

}
