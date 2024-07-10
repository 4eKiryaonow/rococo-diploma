package qa.guru.rococo_painting.ex;

public class NoRestResponseException extends RuntimeException {
    public NoRestResponseException(String message) {
        super(message);
    }
}
