package qa.guru.rococo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qa.guru.rococo.model.ArtistJson;
import qa.guru.rococo.service.api.RestArtistClient;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);

    private final RestArtistClient restArtistClient;

    @Autowired
    public ArtistController(RestArtistClient restArtistClient) {
        this.restArtistClient = restArtistClient;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<ArtistJson> getAllArtists(@PageableDefault Pageable pageable) {
        return restArtistClient.getAllArtists(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ArtistJson getArtistById(@PathVariable String id) {
        return restArtistClient.getArtist(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    ArtistJson addArtist(@RequestBody ArtistJson artist) {
        return restArtistClient.addArtist(artist);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    ArtistJson editArtist(@RequestBody ArtistJson artist) {
        return restArtistClient.editArtist(artist);
    }
}
