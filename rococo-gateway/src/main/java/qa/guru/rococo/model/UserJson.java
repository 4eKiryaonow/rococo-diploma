package qa.guru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;
import qa.guru.rococo.config.RococoGatewayServiceConfig;

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
}
