package qa.guru.rococo_geo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import qa.guru.rococo_geo.data.GeoEntity;

import java.util.UUID;

public record GeoJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("city")
        String name,
        @JsonProperty("country")
        String country) {

    public static GeoJson fromEntity(GeoEntity geoEntity) {
        return new GeoJson(
                geoEntity.getId(),
                geoEntity.getName(),
                geoEntity.getCountry().getName()
        );
    }
}
