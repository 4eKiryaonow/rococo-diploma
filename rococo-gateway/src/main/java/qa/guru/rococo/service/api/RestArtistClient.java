package qa.guru.rococo.service.api;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qa.guru.rococo.ex.NoRestResponseException;
import qa.guru.rococo.model.ArtistJson;
import qa.guru.rococo.model.page.RestPage;

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
        ).orElseThrow(() -> new NoRestResponseException("No REST ArtistJson response is given [/api/artist/{id} Route]"));
    }

    public @Nonnull Page<ArtistJson> getAllArtists(String name, Pageable pageable) {
        if (Objects.isNull(name)) {
            return Optional.ofNullable(
                    restTemplate
                            .getForObject(
                                    rococoArtistApiUri + "?size={size}&page={page}",
                                    RestPage.class,
                                    pageable.getPageSize(),
                                    pageable.getPageNumber())
            ).orElseThrow(() -> new NoRestResponseException("No REST ArtistJson response is given [/api/artist/ Route]"));
        }
        return Optional.ofNullable(
                restTemplate
                        .getForObject(
                                rococoArtistApiUri + "?name={title}",
                                RestPage.class,
                                name,
                                pageable.getPageSize(),
                                pageable.getPageNumber())
        ).orElseThrow(() -> new NoRestResponseException("No REST ArtistJson response is given [/api/artist/ Route]"));

    }

    public @Nonnull ArtistJson addArtist(ArtistJson artistJson) {
        return Optional.ofNullable(
                restTemplate.postForObject(
                        rococoArtistApiUri + "/add",
                        artistJson,
                        ArtistJson.class)
        ).orElseThrow(() -> new NoRestResponseException("No REST ArtistJson response is given [/api/artist/ Route]")
        );
    }

    public @Nonnull ArtistJson editArtist(ArtistJson artistJson) {
        return Optional.ofNullable(
                restTemplate.patchForObject(
                        rococoArtistApiUri + "/edit",
                        artistJson,
                        ArtistJson.class
                )
        ).orElseThrow(() -> new NoRestResponseException("No REST ArtistJson response is given [/api/artist/ Route]")
        );
    }
}
