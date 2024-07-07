package qa.guru.rococo_geo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import qa.guru.rococo_geo.model.CountryJson;
import qa.guru.rococo_geo.model.GeoJson;
import qa.guru.rococo_geo.service.GeoService;

@RestController
@RequestMapping("/api/country")
public class GeoController {

    private GeoService geoService;

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


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    GeoJson addGeo(@RequestBody GeoJson geoJson) {
        return geoService.addGeo(geoJson);
    }
}
