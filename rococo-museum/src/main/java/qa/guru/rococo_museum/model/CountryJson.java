package qa.guru.rococo_museum.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CountryJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name,
        @JsonProperty("code")
        String code) {
}

