package qa.guru.rococo.api.museum;

import qa.guru.rococo.api.pageable.RestResponsePage;
import qa.guru.rococo.model.MuseumJson;
import retrofit2.Call;
import retrofit2.http.*;

public interface MuseumApi {

    @GET("/internal/museum")
    Call<RestResponsePage> getAllMuseums(@Query("size") Integer size,
                                         @Query("page") Integer page);

    @GET("/internal/museum/{id}")
    Call<MuseumJson> getMuseum(@Path("id") String id);

    @POST("/internal/museum/add")
    Call<MuseumJson> addMuseum(@Body MuseumJson museumJson);

    @PATCH("/internal/museum/edit")
    Call<MuseumJson> editMuseum(@Body MuseumJson museumJson);
}
