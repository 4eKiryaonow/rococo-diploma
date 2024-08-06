package qa.guru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import qa.guru.rococo.data.entity.CountryEntity;

import java.util.UUID;

public record CountryJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name) {

    public static CountryJson fromEntity(CountryEntity countryEntity) {
        return new CountryJson(
                countryEntity.getId(),
                countryEntity.getName()
        );
    }
}

