package qa.guru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;
import qa.guru.rococo.config.RococoGatewayServiceConfig;

import java.util.Objects;
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
        @Size(max = RococoGatewayServiceConfig.FIVE_MB)
        @JsonProperty("avatar")
        String avatar
) {
    public @Nonnull UserJson addUsername(@Nonnull String username) {
        return new UserJson(id, username, firstname, surname, avatar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJson userJson = (UserJson) o;
        return Objects.equals(id, userJson.id) && Objects.equals(username, userJson.username) && Objects.equals(firstname, userJson.firstname) && Objects.equals(surname, userJson.username) && Objects.equals(avatar, userJson.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstname, surname, avatar);
    }
}
