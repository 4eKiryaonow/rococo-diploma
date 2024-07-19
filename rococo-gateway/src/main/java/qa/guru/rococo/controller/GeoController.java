package qa.guru.rococo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import qa.guru.rococo.model.CountryJson;
import qa.guru.rococo.service.api.RestGeoClient;

@RestController
@RequestMapping("/api/country")
public class GeoController {

    private static final Logger LOG = LoggerFactory.getLogger(GeoController.class);

    private final RestGeoClient restGeoClient;

    @Autowired
    public GeoController(RestGeoClient restGeoClient) {
        this.restGeoClient = restGeoClient;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<CountryJson> getAllCountries(@PageableDefault Pageable pageable) {
        return restGeoClient.getAllCountries(pageable);
    }
}
