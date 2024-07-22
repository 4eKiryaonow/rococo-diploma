package qa.guru.rococo.service.api;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import qa.guru.rococo.ex.NoRestResponseException;
import qa.guru.rococo.model.UserJson;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

@Service
public class RestUserDataClient  {

    private final RestTemplate restTemplate;
    private final String rococoUserdataBaseUri;

    @Autowired
    public RestUserDataClient(RestTemplate restTemplate,
                              @Value("${rococo-userdata.base-uri}") String rococoUserdataBaseUri) {
        this.restTemplate = restTemplate;
        this.rococoUserdataBaseUri = rococoUserdataBaseUri + "/internal/user";
    }

    public @Nonnull
    UserJson currentUser(@Nonnull String username) {
        return Optional.ofNullable(
                restTemplate.getForObject(
                        rococoUserdataBaseUri + "?username={username}",
                        UserJson.class,
                        username
                )
        ).orElseThrow(() -> new NoRestResponseException("No REST UserJson response is given [/api/user/ Route]"));
    }

    public @Nonnull
    UserJson updateUserInfo(@Nonnull UserJson user) {
        return Optional.ofNullable(
                restTemplate.patchForObject(
                        rococoUserdataBaseUri,
                        user,
                        UserJson.class
                )
        ).orElseThrow(() -> new NoRestResponseException("No REST UserJson response is given [/api/user/ Route]"));
    }
}