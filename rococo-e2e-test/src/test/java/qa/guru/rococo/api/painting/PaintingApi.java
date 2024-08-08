package qa.guru.rococo.api.painting;

import qa.guru.rococo.api.pageable.RestResponsePage;
import qa.guru.rococo.model.PaintingJson;
import retrofit2.Call;
import retrofit2.http.*;

public interface PaintingApi {

    @GET("/internal/painting")
    Call<RestResponsePage> getAllPaintings(@Query("size") Integer size,
                                           @Query("page") Integer page);

    @GET("/internal/painting/{id}")
    Call<PaintingJson> getPainting(@Path("id") String id);

    @POST("/internal/painting/add")
    Call<PaintingJson> addPainting(@Body PaintingJson paintingJson);

    @PATCH("/internal/painting/edit")
    Call<PaintingJson> editPainting(@Body PaintingJson paintingJson);
}
