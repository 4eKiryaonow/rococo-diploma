package qa.guru.rococo_museum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import qa.guru.rococo_museum.data.MuseumEntity;

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
                return new MuseumJson(
                        museumEntity.getId(),
                        museumEntity.getTitle(),
                        museumEntity.getDescription(),
                        museumEntity.getPhoto(),
                        geoJson
                );
        }
}
