package qa.guru.rococo.api.auth;


import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.extension.ExtensionContext;
import qa.guru.rococo.api.ApiClient;
import qa.guru.rococo.api.interceptor.CodeInterceptor;
import qa.guru.rococo.jupiter.extension.ApiLoginExtension;

import java.io.IOException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AuthApiClient extends ApiClient {

    private final AuthApi authApi = retrofit.create(AuthApi.class);

    public AuthApiClient() {
        super(
                CFG.authUrl(),
                true,
                new CodeInterceptor()
        );
    }

    public void doLogin(ExtensionContext context, String username, String password) throws IOException {
        authApi.authorize(
                "code",
                "client",
                "openid",
                CFG.frontUrl() + "authorized",
                ApiLoginExtension.getCodeChallenge(context),
                "S256"
        ).execute();

        authApi.login(
                username,
                password,
                ApiLoginExtension.getCsrf()
        ).execute();

        JsonNode response = authApi.token(
                "Basic " + new String(Base64.getEncoder().encode("client:secret".getBytes(UTF_8))),
                "client",
                CFG.frontUrl() + "authorized",
                "authorization_code",
                ApiLoginExtension.getCode(context),
                ApiLoginExtension.getCodeVerifier(context)
        ).execute().body();

        ApiLoginExtension.setToken(
                context,
                response.get("id_token").asText()
        );
    }

    public void register(String username, String password) throws IOException {
        authApi.requestRegForm().execute();
        authApi.register(
                username,
                password,
                password,
                ApiLoginExtension.getCsrf()
        ).execute();
    }
}