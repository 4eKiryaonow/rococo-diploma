package qa.guru.rococo.service.api;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import qa.guru.rococo.ex.NoRestResponseException;
import qa.guru.rococo.model.PaintingJson;
import qa.guru.rococo.service.RestResponsePage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class RestPaintingClient {

    private final RestTemplate restTemplate;
    private final String rococoPaintingUri;

    public RestPaintingClient(RestTemplate restTemplate,
                              @Value("${rococo-painting.base-uri}") String rococoPaintingUri) {
        this.restTemplate = restTemplate;
        this.rococoPaintingUri = rococoPaintingUri + "/api/painting";
    }

    public @Nonnull Page<PaintingJson> getAllPaintings(Pageable pageable) {
        String uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingUri)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .toUriString();

        return new RestResponsePage<>(List.of(
                Optional.ofNullable(
                        restTemplate
                                .getForObject(uri, PaintingJson[].class)
                ).orElseThrow(() -> new NoRestResponseException("No REST PaintingJson response is given [/api/painting/ Route]"))
        ));
    }

    public @Nonnull PaintingJson getPainting(@Nonnull String id) {
        return Optional.of(
                Objects.requireNonNull(restTemplate.getForObject(
                        rococoPaintingUri + "/{id}",
                        PaintingJson.class,
                        id
                ))
        ).orElseThrow(() -> new NoRestResponseException("No REST PaintingJson response is given [/api/painting/{id} Route]"));
    }

    public @Nonnull PaintingJson addPainting(PaintingJson paintingJson) {
        return Optional.ofNullable(
                restTemplate.postForObject(
                        rococoPaintingUri + "/add",
                        paintingJson,
                        PaintingJson.class)
        ).orElseThrow(() -> new NoRestResponseException("No REST PaintingJson response is given [/api/painting/add Route]")
        );
    }

    public @Nonnull PaintingJson editPainting(PaintingJson paintingJson) {
        return Optional.ofNullable(
                restTemplate.patchForObject(
                        rococoPaintingUri + "/edit",
                        paintingJson,
                        PaintingJson.class)
        ).orElseThrow(() -> new NoRestResponseException("No REST PaintingJson response is given [/api/painting/edit Route]")
        );
    }

    public @Nonnull Page<PaintingJson> getPaintingByAuthorId(@Nonnull String id, Pageable pageable) {
        String uri = UriComponentsBuilder.fromHttpUrl(rococoPaintingUri + "/author/{id}")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .toUriString();

        return new RestResponsePage<>(List.of(
                Optional.of(
                        Objects.requireNonNull(restTemplate.getForObject(
                                uri,
                                PaintingJson[].class,
                                id
                        ))
                ).orElseThrow(() -> new NoRestResponseException("No REST PaintingJson response is given [/api/painting/author/{id} Route]"))));
    }
}
