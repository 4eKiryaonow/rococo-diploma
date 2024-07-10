package qa.guru.rococo_painting.ex;

public class PaintingNotFoundException extends RuntimeException {

    public PaintingNotFoundException(String message) {
        super(message);
    }
}
