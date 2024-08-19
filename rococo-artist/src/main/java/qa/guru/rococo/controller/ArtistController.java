package qa.guru.rococo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qa.guru.rococo.service.ArtistService;
import qa.guru.rococo.model.ArtistJson;

import java.util.UUID;

@RestController
@RequestMapping("/internal/artist")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<ArtistJson> getAllArtists(@RequestParam(required = false) String name,
                                   @PageableDefault Pageable pageable) {
        return artistService.getAllArtists(name, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ArtistJson getArtistById(@PathVariable String id) {
        return artistService.getById(UUID.fromString(id));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    ArtistJson addArtist(@RequestBody ArtistJson artist) {
        return artistService.addArtist(artist);
    }

    @PatchMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    ArtistJson editArtist(@RequestBody ArtistJson artist) {
        return artistService.editArtist(artist);
    }
}
