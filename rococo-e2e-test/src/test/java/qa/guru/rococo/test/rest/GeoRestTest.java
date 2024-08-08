package qa.guru.rococo.test.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.rococo.api.geo.GeoApiClient;
import qa.guru.rococo.jupiter.annotation.GenerateCountry;
import qa.guru.rococo.jupiter.annotation.GenerateGeo;
import qa.guru.rococo.model.CountryJson;
import qa.guru.rococo.model.GeoJson;
import qa.guru.rococo.utils.RandomGenerator;

import java.io.IOException;

public class GeoRestTest {

    private final GeoApiClient geoApiClient = new GeoApiClient();

    @Test
    @DisplayName("REST Country should be available by Id")
    @GenerateCountry
    void getCountryById(CountryJson countryExpected) throws IOException {
        CountryJson countryFact = geoApiClient.getCountry(String.valueOf(countryExpected.id()));
        Assertions.assertEquals(countryExpected, countryFact);
    }

    @Test
    @DisplayName("REST Geo should be available by city and country")
    @GenerateGeo
    void getGeoTest(GeoJson geoExpected) throws IOException {
        GeoJson geoFact = geoApiClient.getGeo(geoExpected.name(), geoExpected.country().name());
        Assertions.assertEquals(geoExpected, geoFact);
    }

    @Test
    @DisplayName("REST Creation existing Geo should return the same")
    @GenerateGeo
    void addExistGeo(GeoJson geoJsonInit) throws IOException {
        GeoJson newGeo = new GeoJson(
                null, geoJsonInit.name(),
                new CountryJson(null, geoJsonInit.country().name()));
        newGeo = geoApiClient.addGeo(newGeo);
        Assertions.assertEquals(geoJsonInit, newGeo);
    }

    @Test
    @DisplayName("REST Create new Geo")
    void addNewGeo() throws IOException {
        GeoJson geoExpected = RandomGenerator.generateUncreatedGeo();
        GeoJson geoFact = geoApiClient.addGeo(geoExpected);
        Assertions.assertNotNull(geoFact.id());
        Assertions.assertEquals(geoExpected.name(), geoFact.name());
        Assertions.assertEquals(geoExpected.country(), geoFact.country());
    }
}
