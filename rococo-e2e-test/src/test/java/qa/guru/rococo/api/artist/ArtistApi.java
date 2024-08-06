package qa.guru.rococo.api.artist;

import qa.guru.rococo.api.pageable.RestResponsePage;
import qa.guru.rococo.model.ArtistJson;
import retrofit2.Call;
import retrofit2.http.*;

public interface ArtistApi {

    @GET("/internal/artist")
    Call<RestResponsePage> getAllArtists(@Query("size") Integer size,
                                         @Query("page") Integer page);

    @GET("/internal/artist/{id}")
    Call<ArtistJson> getArtist(@Path("id") String id);

    @POST("/internal/artist/add")
    Call<ArtistJson> addArtist(@Body ArtistJson artist);

    @PATCH("/internal/artist/edit")
    Call<ArtistJson> editArtist(@Body ArtistJson artist);
}
