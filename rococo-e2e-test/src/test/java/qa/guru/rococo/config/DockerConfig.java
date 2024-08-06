package qa.guru.rococo.config;

public class DockerConfig implements Config {

    static final DockerConfig instance = new DockerConfig();

    private DockerConfig() {}

    @Override
    public String frontUrl() {
        return null;
    }

    @Override
    public String artistUrl() {
        return null;
    }

    @Override
    public String gatewayUrl() {
        return null;
    }

    @Override
    public String geoUrl() {
        return null;
    }

    @Override
    public String museumUrl() {
        return null;
    }

    @Override
    public String paintingUrl() {
        return null;
    }

    @Override
    public String dbHost() {
        return null;
    }
}