package qa.guru.rococo.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import qa.guru.rococo.ex.NoRestResponseException;
import qa.guru.rococo.model.CountryJson;
import qa.guru.rococo.service.RestResponsePage;

import java.util.List;
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

    public Page<CountryJson> getAllCountries(Pageable pageable) {
        String uri = UriComponentsBuilder.fromHttpUrl(rococoGeoBaseUri)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .toUriString();

        return new RestResponsePage<>(List.of(
                Optional.ofNullable(
                        restTemplate
                                .getForObject(uri, CountryJson[].class)
                ).orElseThrow(() -> new NoRestResponseException("No REST CountryJson response is given [/api/country/ Route]"))
        ));
    }
}