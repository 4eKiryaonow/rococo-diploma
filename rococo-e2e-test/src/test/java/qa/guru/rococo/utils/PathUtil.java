package qa.guru.rococo.utils;

import java.util.Objects;

public class PathUtil {

    public String getResourcePath(String resourceName) {
        return Objects.requireNonNull(getClass().getClassLoader().getResource(resourceName)).getPath();
    }
}
