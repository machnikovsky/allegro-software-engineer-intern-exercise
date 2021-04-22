package pl.machnikovsky.internexercise.exception;

public class GithubUserNotFoundException extends RuntimeException {

    private final String username;

    public GithubUserNotFoundException(String username) {
        super("User with a username " + username + " not found.");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
