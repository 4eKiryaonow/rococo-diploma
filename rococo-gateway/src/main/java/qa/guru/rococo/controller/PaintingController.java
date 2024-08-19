package qa.guru.rococo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qa.guru.rococo.model.PaintingJson;
import qa.guru.rococo.service.api.RestPaintingClient;

@RestController
@RequestMapping("/api/painting")
public class PaintingController {

    private static final Logger LOG = LoggerFactory.getLogger(PaintingController.class);

    private final RestPaintingClient restPaintingClient;

    public PaintingController(RestPaintingClient restPaintingClient) {
        this.restPaintingClient = restPaintingClient;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<PaintingJson> getAllPaintings(@RequestParam(required = false) String title,
                                       @PageableDefault Pageable pageable) {
        return restPaintingClient.getAllPaintings(title, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    PaintingJson getPaintingById(@PathVariable String id) {
        return restPaintingClient.getPainting(id);
    }

    @GetMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    Page<PaintingJson> getPaintingsByAuthorId(@PathVariable String id,
                                              @PageableDefault Pageable pageable) {
        return restPaintingClient.getPaintingByAuthorId(id, pageable);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    PaintingJson addPainting(@RequestBody PaintingJson paintingJson) {
        return restPaintingClient.addPainting(paintingJson);
    }

    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    PaintingJson editPainting(@RequestBody PaintingJson paintingJson) {
        return restPaintingClient.editPainting(paintingJson);
    }
}
