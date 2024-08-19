package qa.guru.rococo.service.api;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qa.guru.rococo.ex.NoRestResponseException;
import qa.guru.rococo.model.MuseumJson;
import qa.guru.rococo.model.page.RestPage;

import java.util.Objects;
import java.util.Optional;

@Component
public class RestMuseumClient {

    private final RestTemplate restTemplate;
    private final String rococoMuseumUri;

    public RestMuseumClient(RestTemplate restTemplate,
                            @Value("${rococo-museum.base-uri}") String rococoMuseumUri) {
        this.restTemplate = restTemplate;
        this.rococoMuseumUri = rococoMuseumUri + "/internal/museum";
    }

    public @Nonnull Page<MuseumJson> getAllMuseums(String title, Pageable pageable) {
        if (Objects.isNull(title)) {
            return Optional.ofNullable(
                    restTemplate
                            .getForObject(
                                    rococoMuseumUri + "?size={size}&page={page}",
                                    RestPage.class,
                                    pageable.getPageSize(),
                                    pageable.getPageNumber())
            ).orElseThrow(() -> new NoRestResponseException("No REST Page<MuseumJson> response is given [/api/museum/ Route]"));
        }
        return Optional.ofNullable(
                restTemplate
                        .getForObject(
                                rococoMuseumUri + "?title={title}",
                                RestPage.class,
                                title,
                                pageable.getPageSize(),
                                pageable.getPageNumber())
        ).orElseThrow(() -> new NoRestResponseException("No REST Page<MuseumJson> response is given [/api/museum/ Route]"));
    }

    public @Nonnull MuseumJson getMuseum(@Nonnull String id) {
        return Optional.of(
                Objects.requireNonNull(restTemplate.getForObject(
                        rococoMuseumUri + "/{id}",
                        MuseumJson.class,
                        id
                ))
        ).orElseThrow(() -> new NoRestResponseException("No REST MuseumJson response is given [/api/museum/{id} Route]"));
    }

    public @Nonnull MuseumJson addMuseum(MuseumJson museumJson) {
        return Optional.ofNullable(
                restTemplate.postForObject(
                        rococoMuseumUri + "/add",
                        museumJson,
                        MuseumJson.class)
        ).orElseThrow(() -> new NoRestResponseException("No REST MuseumJson response is given [/api/museum/ Route]")
        );
    }

    public @Nonnull MuseumJson editMuseum(MuseumJson museumJson) {
        return Optional.ofNullable(
                restTemplate.patchForObject(
                        rococoMuseumUri + "/edit",
                        museumJson,
                        MuseumJson.class)
        ).orElseThrow(() -> new NoRestResponseException("No REST MuseumJson response is given [/api/museum/ Route]")
        );
    }
}
