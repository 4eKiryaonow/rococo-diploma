package qa.guru.rococo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RococoGatewayServiceConfig {
    public static final int FIVE_MB = 5 * 1024 * 1024;

    private final String rococoUserdataBaseUri;

    @Autowired
    public RococoGatewayServiceConfig(@Value("${rococo-userdata.base-uri}") String rococoUserdataBaseUri) {
        this.rococoUserdataBaseUri = rococoUserdataBaseUri;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

}
