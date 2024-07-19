package qa.guru.rococo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

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

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(
                        configurer -> configurer.defaultCodecs().maxInMemorySize(FIVE_MB)).build())
                .build();
    }
}
