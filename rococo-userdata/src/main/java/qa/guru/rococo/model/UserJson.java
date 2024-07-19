package qa.guru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import qa.guru.rococo.data.UserEntity;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record UserJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("username")
        String username,
        @JsonProperty("firstname")
        String firstname,
        @JsonProperty("surname")
        String surname,
        @JsonProperty("avatar")
        String avatar
) {
    public static UserJson fromEntity(UserEntity user) {
        byte[] photo = user.getPhoto();
        return new UserJson(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getSurname(),
                photo != null && photo.length > 0 ? new String(user.getPhoto(), StandardCharsets.UTF_8) : null
        );
    }
}
