package qa.guru.rococo.api.geo;

import qa.guru.rococo.model.CountryJson;
import qa.guru.rococo.model.GeoJson;
import retrofit2.Call;
import retrofit2.http.*;

public interface GeoApi {

    @GET("/internal/country/{id}")
    Call<CountryJson> getCountry(@Path("id") String id);

    @GET("/internal/country/geo")
    Call<GeoJson> getGeo(@Query("nameCity") String city,
                         @Query("nameCountry") String country);

    @POST("/internal/country/geo/add")
    Call<GeoJson> addGeo(@Body GeoJson geoJson);
}
