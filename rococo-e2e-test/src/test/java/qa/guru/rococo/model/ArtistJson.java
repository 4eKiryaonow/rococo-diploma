package qa.guru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import qa.guru.rococo.data.entity.ArtistEntity;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record ArtistJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name,
        @JsonProperty("biography")
        String biography,
        @JsonProperty("photo")
        String photo
) {

    public static ArtistJson fromEntity(ArtistEntity artistEntity) {
        byte[] photo = artistEntity.getPhoto();
        return new ArtistJson(
                artistEntity.getId(),
                artistEntity.getName(),
                artistEntity.getBiography(),
                photo != null && photo.length > 0 ? new String(artistEntity.getPhoto(), StandardCharsets.UTF_8) : null
        );
    }
}
