package qa.guru.rococo.api;

import retrofit2.Call;
import retrofit2.http.*;

public interface UserService {

    @GET("/register")
    Call<Void> register();

    @FormUrlEncoded
    @POST(value = "/register")
    Call<Void> register(
            @Header("Cookie") String xsrf,
            @Field("_csrf") String _csrf,
            @Field("username") String username,
            @Field("password") String password,
            @Field("passwordSubmit") String passwordSubmit
    );
}
