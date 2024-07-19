package qa.guru.rococo.ex;

public class NoRestResponseException extends RuntimeException {
    public NoRestResponseException(String message) {
        super(message);
    }
}
