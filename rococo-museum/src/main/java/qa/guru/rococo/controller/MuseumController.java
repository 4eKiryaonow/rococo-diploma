package qa.guru.rococo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qa.guru.rococo.model.MuseumJson;
import qa.guru.rococo.service.MuseumService;

@RestController
@RequestMapping("/internal/museum")
public class MuseumController {

    private final MuseumService museumService;

    public MuseumController(MuseumService museumService) {
        this.museumService = museumService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<MuseumJson> getAllMuseums(@RequestParam(required = false) String title,
                                   @PageableDefault Pageable pageable) {
        return museumService.getAllMuseums(title, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    MuseumJson getMuseumById(@PathVariable String id) {
        return museumService.getMuseumById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    MuseumJson createMuseum(@RequestBody MuseumJson museumJson) {
        return museumService.addMuseum(museumJson);
    }

    @PatchMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    MuseumJson editMuseum(@RequestBody MuseumJson museumJson) {
        return museumService.editMuseum(museumJson);
    }
}
