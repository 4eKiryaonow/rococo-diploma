package qa.guru.rococo_artist.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qa.guru.rococo_artist.model.ArtistJson;
import qa.guru.rococo_artist.service.ArtistService;

import java.util.UUID;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<ArtistJson> getAllArtists(@PageableDefault Pageable pageable) {
        return artistService.getAllArtists(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ArtistJson getArtistById(@PathVariable String id) {
        return artistService.getById(UUID.fromString(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    ArtistJson addArtist(@RequestBody ArtistJson artist) {
        return artistService.addArtist(artist);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    ArtistJson editArtist(@RequestBody ArtistJson artist) {
        return artistService.editArtist(artist);
    }
}
