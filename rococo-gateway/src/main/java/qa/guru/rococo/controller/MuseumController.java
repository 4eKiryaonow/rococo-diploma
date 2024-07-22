package qa.guru.rococo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qa.guru.rococo.model.MuseumJson;
import qa.guru.rococo.service.api.RestMuseumClient;

@RestController
@RequestMapping("/api/museum")
public class MuseumController {

    private static final Logger LOG = LoggerFactory.getLogger(MuseumController.class);

    private final RestMuseumClient restMuseumClient;

    public MuseumController(RestMuseumClient restMuseumClient) {
        this.restMuseumClient = restMuseumClient;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<MuseumJson> getAllMuseums(@RequestParam(required = false) String title,
                                   @PageableDefault Pageable pageable) {
        return restMuseumClient.getAllMuseums(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    MuseumJson getMuseumById(@PathVariable String id) {
        return restMuseumClient.getMuseum(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    MuseumJson createMuseum(@RequestBody MuseumJson museumJson) {
        return restMuseumClient.addMuseum(museumJson);
    }

    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    MuseumJson editMuseum(@RequestBody MuseumJson museumJson) {
        return restMuseumClient.editMuseum(museumJson);
    }
}
