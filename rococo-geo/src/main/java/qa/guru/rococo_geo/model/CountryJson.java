package qa.guru.rococo_geo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import qa.guru.rococo_geo.data.CountryEntity;

import java.util.UUID;

public record CountryJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name,
        @JsonProperty("code")
        String code) {

    public static CountryJson fromEntity(CountryEntity countryEntity) {
        return new CountryJson(
                countryEntity.getId(),
                countryEntity.getName(),
                countryEntity.getCode()
        );
    }
}

