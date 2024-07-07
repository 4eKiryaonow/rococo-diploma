package qa.guru.rococo_artist.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import qa.guru.rococo_artist.data.ArtistEntity;

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
        return new ArtistJson(
                artistEntity.getId(),
                artistEntity.getName(),
                artistEntity.getBiography(),
                artistEntity.getPhoto()
        );
    }
}
