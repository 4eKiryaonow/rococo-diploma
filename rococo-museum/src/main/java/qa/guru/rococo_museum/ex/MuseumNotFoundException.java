package qa.guru.rococo_museum.ex;

public class MuseumNotFoundException extends RuntimeException {

    public MuseumNotFoundException(String message) {
        super(message);
    }
}
