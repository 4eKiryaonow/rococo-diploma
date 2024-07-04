package qa.guru.rococo_auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserJson(
        @JsonProperty("username")
        String username) {

}
