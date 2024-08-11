package qa.guru.rococo.api.userdata;

import qa.guru.rococo.model.UserJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface UserdataApi {

    @GET("/user")
    Call<UserJson> currentUser(@Query("username") String username);

    @PATCH("/user")
    Call<UserJson> editUser(@Body UserJson user);
}
