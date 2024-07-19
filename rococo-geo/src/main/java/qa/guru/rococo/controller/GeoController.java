package qa.guru.rococo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qa.guru.rococo.model.CountryJson;
import qa.guru.rococo.model.GeoJson;
import qa.guru.rococo.service.GeoService;

@RestController
@RequestMapping("/internal/country")
public class GeoController {

    private final GeoService geoService;

    @Autowired
    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<CountryJson> getAllCountries(@PageableDefault Pageable pageable) {
        return geoService.getAllCountries(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CountryJson getCountry(@PathVariable String id) {
        return geoService.getCountryById(id);
    }

    @GetMapping("/geo")
    @ResponseStatus(HttpStatus.OK)
    GeoJson getGeo(@RequestParam String nameCity, @RequestParam String nameCountry) {
        return geoService.getGeo(nameCity, nameCountry);
    }

    @PostMapping("/geo/add")
    @ResponseStatus(HttpStatus.OK)
    GeoJson addGeo(@RequestBody GeoJson geoJson) {
        return geoService.addGeo(geoJson);
    }
}
