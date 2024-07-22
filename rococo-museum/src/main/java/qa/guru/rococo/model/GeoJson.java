package qa.guru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.UUID;


public record GeoJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("city")
        String name,
        @JsonProperty("country")
        CountryJson country) {
}
