package qa.guru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
        UUID artistId,
        @JsonProperty("museum")
        UUID museumId
) {
}