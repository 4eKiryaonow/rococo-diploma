package qa.guru.rococo.service.api;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import qa.guru.rococo.ex.NoRestResponseException;
import qa.guru.rococo.model.PaintingJson;
import qa.guru.rococo.model.page.RestPage;

import java.util.Objects;
import java.util.Optional;

@Component
public class RestPaintingClient {

    private final RestTemplate restTemplate;
    private final String rococoPaintingUri;

    public RestPaintingClient(RestTemplate restTemplate,
                              @Value("${rococo-painting.base-uri}") String rococoPaintingUri) {
        this.restTemplate = restTemplate;
        this.rococoPaintingUri = rococoPaintingUri + "/internal/painting";
    }

    public @Nonnull Page<PaintingJson> getAllPaintings(String title, Pageable pageable) {
        if (Objects.isNull(title)) {
            return Optional.ofNullable(
                    restTemplate
                            .getForObject(
                                    rococoPaintingUri + "?size={size}&page={page}",
                                    RestPage.class,
                                    pageable.getPageSize(),
                                    pageable.getPageNumber())
            ).orElseThrow(() -> new NoRestResponseException("No REST PaintingJson response is given [/api/painting/ Route]"));
        }
        return Optional.ofNullable(
                restTemplate
                        .getForObject(
                                rococoPaintingUri + "?title={title}",
                                RestPage.class,
                                title,
                                pageable.getPageSize(),
                                pageable.getPageNumber())
        ).orElseThrow(() -> new NoRestResponseException("No REST PaintingJson response is given [/api/painting/ Route]"));

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
        return Optional.of(
                Objects.requireNonNull(restTemplate.getForObject(
                        rococoPaintingUri + "/author/{id}?size={size}&page={page}",
                        RestPage.class,
                        id,
                        pageable.getPageSize(),
                        pageable.getPageNumber()
                ))
        ).orElseThrow(() -> new NoRestResponseException("No REST PaintingJson response is given [/api/painting/author/{id} Route]"));
    }
}
