package pl.machnikovsky.internexercise.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.machnikovsky.internexercise.exception.GithubUserNotFoundException;
import pl.machnikovsky.internexercise.model.RepositoryEntity;
import pl.machnikovsky.internexercise.service.GithubService;

import java.util.List;

import static org.mockito.Mockito.*;
import static pl.machnikovsky.internexercise.controller.MockValues.getJsonObjects;

@ExtendWith(MockitoExtension.class)
public class GithubControllerUnitTest {

    GithubController githubController;
    GithubService githubService;

    @BeforeEach
    void init() {
        githubService = mock(GithubService.class);
        githubController = new GithubController(githubService);
    }

    @Test
    public void shouldReturnAllRepositories() {
        //given
        doReturn(getJsonObjects()).when(githubService).getAllReposAtOnePage(" ");

        //when
        ResponseEntity<List<RepositoryEntity>> allReposAtOnePage = githubController.getAllReposAtOnePage(" ");

        //then
        Assertions.assertEquals(4, allReposAtOnePage.getBody().size());
        Assertions.assertEquals(HttpStatus.OK, allReposAtOnePage.getStatusCode());
    }

    @Test
    public void shouldNotReturnAllRepositories() {
        //given
        doThrow(GithubUserNotFoundException.class).when(githubService).getAllReposAtOnePage("");

        //when
        ResponseEntity<List<RepositoryEntity>> allReposAtOnePage = githubController.getAllReposAtOnePage(" ");

        //then
        Assertions.assertEquals(4, allReposAtOnePage.getBody().size());
        Assertions.assertEquals(HttpStatus.OK, allReposAtOnePage.getStatusCode());
    }


}
