package qa.guru.rococo.data;

import qa.guru.rococo.config.Config;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DataBase {
    ARTIST("jdbc:postgresql://%s:%d/rococo-artist"),
    MUSEUM("jdbc:postgresql://%s:%d/rococo-museum"),
    GEO("jdbc:postgresql://%s:%d/rococo-geo"),
    PAINTING("jdbc:postgresql://%s:%d/rococo-painting"),
    AUTH("jdbc:postgresql://%s:%d/rococo-auth"),
    USERDATA("jdbc:postgresql://%s:%d/rococo-userdata");

    private final String JdbcURL;
    private final static Config CFG = Config.getInstance();

    public String getJdbcURL() {
        return String.format(JdbcURL, CFG.dbHost(), CFG.dbPort());
    }

}
