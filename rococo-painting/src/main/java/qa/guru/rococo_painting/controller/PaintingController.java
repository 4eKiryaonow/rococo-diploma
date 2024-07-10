package qa.guru.rococo_painting.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qa.guru.rococo_painting.model.PaintingJson;
import qa.guru.rococo_painting.service.PaintingService;

@RestController
@RequestMapping("/api/painting")
public class PaintingController {

    private final PaintingService paintingService;

    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<PaintingJson> getAllPaintings(@RequestParam(required = false) String title,
                                       @PageableDefault Pageable pageable) {
        return paintingService.getAllPaintings(title, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    PaintingJson getPaintingById(@PathVariable String id) {
        return paintingService.getPaintingById(id);
    }

    @GetMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    Page<PaintingJson> getPaintingsByAuthorId(@PathVariable String id,
                                              @PageableDefault Pageable pageable) {
        return paintingService.getPaintingByAuthorId(id, pageable);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    PaintingJson addPainting(@RequestBody PaintingJson paintingJson) {
        return paintingService.addPainting(paintingJson);
    }

    @PatchMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    PaintingJson editPainting(@RequestBody PaintingJson paintingJson) {
        return paintingService.editPainting(paintingJson);
    }
}
