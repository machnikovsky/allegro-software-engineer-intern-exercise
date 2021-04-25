package pl.machnikovsky.internexercise.exception;

public class RequestLimitExceededException extends RuntimeException {

    public RequestLimitExceededException() {
        super("The request per hour limit has been exceeded.");
    }
}
