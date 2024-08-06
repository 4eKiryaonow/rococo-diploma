package qa.guru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import qa.guru.rococo.data.entity.GeoEntity;

import java.util.UUID;
public record GeoJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("city")
        String name,
        @JsonProperty("country")
        CountryJson country) {

    public static GeoJson fromEntity(GeoEntity geoEntity) {
        return new GeoJson(
                geoEntity.getId(),
                geoEntity.getName(),
                new CountryJson(
                        geoEntity.getCountry().getId(),
                        geoEntity.getCountry().getName()
                )
        );
    }
}
