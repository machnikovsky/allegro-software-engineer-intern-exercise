package pl.machnikovsky.internexercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GithubExceptionHandler {

    @ResponseBody
    @ExceptionHandler(GithubUserNotFoundException.class)
    public ResponseEntity<Object> handleGithubUserNotFoundException(GithubUserNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        GithubUserNotFoundEntity githubUserNotFoundEntity = new GithubUserNotFoundEntity(
                e.getMessage(),
                status,
                status.value()
        );
        return new ResponseEntity<>(githubUserNotFoundEntity, status);

    }
}
