package pl.machnikovsky.internexercise.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class GithubUserNotFoundEntity {

    private final String message;
    private final HttpStatus httpStatus;
    private final int statusCode;

    public GithubUserNotFoundEntity(String message, HttpStatus httpStatus, int statusCode) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
