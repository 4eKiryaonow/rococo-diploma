package qa.guru.rococo.service.api;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qa.guru.rococo.ex.NoRestResponseException;
import qa.guru.rococo.model.CountryJson;
import qa.guru.rococo.model.GeoJson;

import java.util.Objects;
import java.util.Optional;

@Component
public class RestGeoClient {

    private final RestTemplate restTemplate;
    private final String rococoGeoApiUri;

    @Autowired
    public RestGeoClient(RestTemplate restTemplate, @Value("${rococo-geo.base-uri}") String rococoGeoApiUri) {
        this.restTemplate = restTemplate;
        this.rococoGeoApiUri = rococoGeoApiUri + "/internal/country";
    }

    public @Nonnull CountryJson getCountry(@Nonnull String id) {
        return Optional.of(
                Objects.requireNonNull(restTemplate.getForObject(
                        rococoGeoApiUri + "/{id}",
                        CountryJson.class,
                        id
                ))
        ).orElseThrow(() -> new NoRestResponseException("No REST CountryJson response is given [/api/country/ Route]"));
    }

    public @Nonnull GeoJson addGeo(@Nonnull GeoJson geoJson) {
        return Optional.ofNullable(
                restTemplate.postForObject(
                        rococoGeoApiUri + "/geo/add",
                        geoJson,
                        GeoJson.class)
        ).orElseThrow(() -> new NoRestResponseException("No REST GeoJson response is given [/api/country/ Route]"));
    }

    public @Nonnull GeoJson getGeo(@Nonnull String nameCity, @Nonnull String nameCountry) {
        return Optional.ofNullable(
                restTemplate.getForObject(
                        rococoGeoApiUri + "/geo?nameCity={nameCity}&nameCountry={nameCountry}",
                        GeoJson.class,
                        nameCity,
                        nameCountry
                )
        ).orElseThrow(() -> new NoRestResponseException("No REST GeoJson response is given [/api/country/ Route]"));
    }
}
