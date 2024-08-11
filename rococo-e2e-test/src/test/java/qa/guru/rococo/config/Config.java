package qa.guru.rococo.config;

public interface Config {

    static Config getInstance() {
        String env = System.getProperty("test.env", "local");

        return switch (env) {
            case "local" -> LocalConfig.instance;
            case "docker" -> DockerConfig.instance;
            default -> throw new IllegalStateException("Can not find config");
        };
    }

    String frontUrl();

    String artistUrl();
    String gatewayUrl();
    String geoUrl();
    String museumUrl();
    String paintingUrl();

    String userdataUrl();

    String authUrl();

    String dbHost();


    default int dbPort() {
        return 5432;
    }
}
