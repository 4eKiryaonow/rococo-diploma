package qa.guru.rococo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import qa.guru.rococo.model.UserJson;
import qa.guru.rococo.service.UserdataService;

@RestController
@RequestMapping("/internal/user")
public class UserdataController {

    private static final Logger LOG = LoggerFactory.getLogger(UserdataController.class);

    private final UserdataService userdataService;

    @Autowired
    public UserdataController(UserdataService userService) {
        this.userdataService = userService;
    }

    @GetMapping()
    public UserJson currentUser(@RequestParam String username) {
        return userdataService.getCurrentUser(username);
    }

    @PatchMapping()
    public UserJson updateUserInfo(@RequestBody UserJson user) {
        return userdataService.update(user);
    }
}
