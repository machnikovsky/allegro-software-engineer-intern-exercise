package pl.machnikovsky.internexercise.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.machnikovsky.internexercise.exception.GithubUserNotFoundException;
import pl.machnikovsky.internexercise.model.RepositoryEntity;
import pl.machnikovsky.internexercise.service.GithubService;

import java.util.List;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
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
        given(githubService.getAllReposAtOnePage("test")).willThrow(new GithubUserNotFoundException("test"));

        //when
        when(() -> githubService.getAllReposAtOnePage("test"));

        //then
        then(caughtException()).isInstanceOf(GithubUserNotFoundException.class);
    }

    @Test
    public void shouldReturnUserStars() {
        //given
        doReturn(ResponseEntity.ok(350)).when(githubService).getUserStars("test");

        //when
        ResponseEntity<Integer> stars = githubController.getUserStars("test");

        //then
        Assertions.assertEquals(350, stars.getBody().intValue());
        Assertions.assertEquals(HttpStatus.OK, stars.getStatusCode());
    }

    @Test
    public void shouldNotReturnUserStars() {
        //given
        given(githubService.getUserStars("test")).willThrow(new GithubUserNotFoundException("test"));

        //when
        when(() -> githubService.getUserStars("test"));

        //then
        then(caughtException()).isInstanceOf(GithubUserNotFoundException.class);
    }


}
