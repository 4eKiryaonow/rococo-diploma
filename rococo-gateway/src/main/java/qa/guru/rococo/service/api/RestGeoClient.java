package qa.guru.rococo.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qa.guru.rococo.ex.NoRestResponseException;
import qa.guru.rococo.service.RestResponsePage;

import java.util.Optional;

@Component
public class RestGeoClient {
    private final RestTemplate restTemplate;
    private final String rococoGeoBaseUri;

    @Autowired
    public RestGeoClient(RestTemplate restTemplate,
                         @Value("${rococo-geo.base-uri}") String rococoGeoBaseUri) {
        this.restTemplate = restTemplate;
        this.rococoGeoBaseUri = rococoGeoBaseUri + "/internal/country";
    }

    public RestResponsePage getAllCountries(Pageable pageable) {
        return Optional.ofNullable(
                restTemplate.getForObject(
                        rococoGeoBaseUri + "?size={size}&page={page}",
                        RestResponsePage.class,
                        pageable.getPageSize(),
                        pageable.getPageNumber())
        ).orElseThrow(() -> new NoRestResponseException("No REST Page<CountryJson> response is given [/api/country/ Route]"));
    }
}