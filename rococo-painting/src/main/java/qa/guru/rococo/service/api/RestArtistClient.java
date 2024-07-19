package qa.guru.rococo.service.api;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qa.guru.rococo.ex.NoRestResponseException;
import qa.guru.rococo.model.ArtistJson;

import java.util.Objects;
import java.util.Optional;

@Component
public class RestArtistClient {

    private final RestTemplate restTemplate;
    private final String rococoArtistApiUri;

    public RestArtistClient(RestTemplate restTemplate,
                            @Value("${rococo-artist.base-uri}") String rococoArtistApiUri) {
        this.restTemplate = restTemplate;
        this.rococoArtistApiUri = rococoArtistApiUri + "/internal/artist";
    }

    public @Nonnull ArtistJson getArtist(@Nonnull String id) {
        return Optional.of(
                Objects.requireNonNull(restTemplate.getForObject(
                        rococoArtistApiUri + "/{id}",
                        ArtistJson.class,
                        id
                ))
        ).orElseThrow(() -> new NoRestResponseException("No REST ArtistJson response is given [/api/artist/ Route]"));
    }
}
