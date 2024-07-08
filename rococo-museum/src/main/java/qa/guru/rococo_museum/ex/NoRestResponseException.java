package qa.guru.rococo_museum.ex;

public class NoRestResponseException extends RuntimeException {
    public NoRestResponseException(String message) {
        super(message);
    }
}
