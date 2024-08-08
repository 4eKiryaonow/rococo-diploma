package qa.guru.rococo.api.geo;

import qa.guru.rococo.api.ApiClient;
import qa.guru.rococo.model.CountryJson;
import qa.guru.rococo.model.GeoJson;

import java.io.IOException;

public class GeoApiClient extends ApiClient {

    private GeoApi geoApi;

    public GeoApiClient() {
        super(CFG.geoUrl());
        this.geoApi = retrofit.create(GeoApi.class);
    }

    public CountryJson getCountry(String id) throws IOException {
        return geoApi.getCountry(id).execute().body();
    }

    public GeoJson getGeo(String city, String country) throws IOException {
        return geoApi.getGeo(city, country).execute().body();
    }

    public GeoJson addGeo(GeoJson geoJson) throws IOException {
        return geoApi.addGeo(geoJson).execute().body();
    }
}
