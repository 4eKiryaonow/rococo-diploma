package qa.guru.rococo.ex;

public class MuseumNotFoundException extends RuntimeException {

    public MuseumNotFoundException(String message) {
        super(message);
    }
}
