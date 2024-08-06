package qa.guru.rococo.config;

public class LocalConfig implements Config {

    static final LocalConfig instance = new LocalConfig();

    private LocalConfig() {}

    @Override
    public String frontUrl() {
        return "http://127.0.0.1:3000/";
    }

    @Override
    public String artistUrl() {
        return "http://127.0.0.1:9002/";
    }

    @Override
    public String gatewayUrl() {
        return "http://127.0.0.1:8080/";
    }

    @Override
    public String geoUrl() {
        return "http://127.0.0.1:9001/";
    }

    @Override
    public String museumUrl() {
        return "http://127.0.0.1:9003/";
    }

    @Override
    public String paintingUrl() {
        return "http://127.0.0.1:9004/";
    }

    @Override
    public String dbHost() {
        return "127.0.0.1";
    }
}
