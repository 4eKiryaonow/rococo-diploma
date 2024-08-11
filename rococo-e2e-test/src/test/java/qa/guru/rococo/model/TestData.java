package qa.guru.rococo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record TestData(
        @JsonIgnore String password
) {


}
