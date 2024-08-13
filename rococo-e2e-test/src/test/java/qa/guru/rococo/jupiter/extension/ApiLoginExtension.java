package qa.guru.rococo.jupiter.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.openqa.selenium.Cookie;
import qa.guru.rococo.api.auth.AuthApiClient;
import qa.guru.rococo.api.context.ThreadLocalCookieStore;
import qa.guru.rococo.config.Config;
import qa.guru.rococo.jupiter.annotation.ApiLogin;
import qa.guru.rococo.jupiter.annotation.TestUser;
import qa.guru.rococo.jupiter.annotation.User;
import qa.guru.rococo.model.UserJson;
import qa.guru.rococo.utils.OauthUtils;

import java.util.Map;


public class ApiLoginExtension implements BeforeEachCallback, AfterEachCallback {

    private static final Config CFG = Config.getInstance();

    public static final ExtensionContext.Namespace API_LOGIN_NAMESPACE = ExtensionContext.Namespace.create(ApiLoginExtension.class);
    private final AuthApiClient authApiClient = new AuthApiClient();


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ApiLogin apiLogin = AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                ApiLogin.class
        ).orElse(null);

        final String login;
        final String password;

        if (apiLogin != null) {
            TestUser testUser = apiLogin.user();
            if (!testUser.fake()) {
                UserJson createdUserForApiLogin = getCreatedUserForApiLogin(context);
                login = createdUserForApiLogin.username();
                password = createdUserForApiLogin.testData().password();
            } else {
                login = apiLogin.username();
                password = apiLogin.password();
            }

            final String codeVerifier = OauthUtils.generateCodeVerifier();
            final String codeChallenge = OauthUtils.generateCodeChallenge(codeVerifier);
            setCodeVerifier(context, codeVerifier);
            setCodeChallenge(context, codeChallenge);
            authApiClient.doLogin(context, login, password);

            Selenide.open(CFG.frontUrl());
            Selenide.localStorage().setItem("id_token", getToken(context));
            WebDriverRunner.getWebDriver().manage().addCookie(getJsessionIdCookie());
            Selenide.refresh();
            }
        }


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        ThreadLocalCookieStore.INSTANCE.removeAll();
    }

    public static void setCodeVerifier(ExtensionContext extensionContext, String codeVerifier) {
        extensionContext.getStore(API_LOGIN_NAMESPACE).put("cv", codeVerifier);
    }

    public static String getCodeVerifier(ExtensionContext extensionContext) {
        return extensionContext.getStore(API_LOGIN_NAMESPACE).get("cv", String.class);
    }

    public static void setCodeChallenge(ExtensionContext context, String codeChallenge) {
        context.getStore(API_LOGIN_NAMESPACE).put("cc", codeChallenge);
    }

    public static String getCodeChallenge(ExtensionContext context) {
        return context.getStore(API_LOGIN_NAMESPACE).get("cc", String.class);
    }

    public static void setCode(ExtensionContext context, String code) {
        context.getStore(API_LOGIN_NAMESPACE).put("code", code);
    }

    public static String getCode(ExtensionContext context) {
        return context.getStore(API_LOGIN_NAMESPACE).get("code", String.class);
    }

    public static void setToken(ExtensionContext context, String token) {
        context.getStore(API_LOGIN_NAMESPACE).put("token", token);
    }

    public static String getToken(ExtensionContext context) {
        return context.getStore(API_LOGIN_NAMESPACE).get("token", String.class);
    }

    public static String getBearerToken(ExtensionContext context) {
        return "Bearer " + getToken(context);
    }

    public static String getCsrf() {
        return ThreadLocalCookieStore.INSTANCE.cookieValue("XSRF-TOKEN");
    }

    public static String getJsessionid() {
        return ThreadLocalCookieStore.INSTANCE.cookieValue("JSESSIONID");
    }

    public Cookie getJsessionIdCookie() {
        return new Cookie(
                "JSESSIONID",
                getJsessionid()
        );
    }

    @SuppressWarnings("unchecked")
    private static UserJson getCreatedUserForApiLogin(ExtensionContext context) {
        Map<User.Point, UserJson> createdUsers = (Map<User.Point, UserJson>) context.getStore(GenerateUserExtension.GENERATE_USER_NAMESPACE)
                .get(context.getUniqueId(), Map.class);
        if (createdUsers == null) {
            throw new IllegalStateException("No users were created or stored in the context.");
        }

        UserJson userJson = createdUsers.get(User.Point.INNER);
        if (userJson == null) {
            throw new IllegalStateException("No UserJson found for the given point: " + User.Point.INNER);
        }

        return userJson;
    }
}
