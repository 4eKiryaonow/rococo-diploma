package qa.guru.rococo.controller;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import qa.guru.rococo.model.UserJson;
import qa.guru.rococo.service.api.RestUserDataClient;

@RestController
@RequestMapping("/api/user")
public class UserdataController {

    private static final Logger LOG = LoggerFactory.getLogger(UserdataController.class);

    private final RestUserDataClient restUserDataClient;

    @Autowired
    public UserdataController(RestUserDataClient restUserDataClient) {
        this.restUserDataClient = restUserDataClient;
    }

    @GetMapping()
    public UserJson currentUser(@AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        return restUserDataClient.currentUser(username);
    }

    @PatchMapping()
    public UserJson updateUserInfo(@AuthenticationPrincipal Jwt principal, @Valid @RequestBody UserJson user) {
        String username = principal.getClaim("sub");
        return restUserDataClient.updateUserInfo(user.addUsername(username));
    }
}
