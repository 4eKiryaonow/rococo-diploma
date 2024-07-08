package qa.guru.rococo_museum.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record GeoJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("city")
        String name,
        @JsonProperty("country")
        CountryJson country) {
}
