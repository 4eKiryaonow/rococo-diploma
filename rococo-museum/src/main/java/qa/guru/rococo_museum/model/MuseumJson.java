package qa.guru.rococo_museum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import qa.guru.rococo_museum.data.MuseumEntity;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record MuseumJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("title")
        String title,
        @JsonProperty("description")
        String description,
        @JsonProperty("photo")
        String photo,
        @JsonProperty("geo")
        GeoJson geo
) {
    public static MuseumJson fromEntity(MuseumEntity museumEntity, GeoJson geoJson) {
        byte[] photo = museumEntity.getPhoto();
        return new MuseumJson(
                museumEntity.getId(),
                museumEntity.getTitle(),
                museumEntity.getDescription(),
                photo != null && photo.length > 0 ? new String(museumEntity.getPhoto(), StandardCharsets.UTF_8) : null,
                geoJson
        );
    }
}
