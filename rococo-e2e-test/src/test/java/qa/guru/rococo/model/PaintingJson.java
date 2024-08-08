package qa.guru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import qa.guru.rococo.data.entity.PaintingEntity;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record PaintingJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("title")
        String title,
        @JsonProperty("content")
        String content,
        @JsonProperty("description")
        String description,
        @JsonProperty("artist")
        ArtistJson artistJson,
        @JsonProperty("museum")
        MuseumJson museumJson
) {
    public static PaintingJson fromEntity(PaintingEntity entity) {
        byte[] photo = entity.getContent();
        return new PaintingJson(
                entity.getId(),
                entity.getTitle(),
                photo != null && photo.length > 0 ? new String(entity.getContent(), StandardCharsets.UTF_8) : null,
                entity.getDescription(),
                new ArtistJson(entity.getArtistId(), null, null, null),
                new MuseumJson(entity.getMuseumId(), null, null, null, null)
        );
    }
}