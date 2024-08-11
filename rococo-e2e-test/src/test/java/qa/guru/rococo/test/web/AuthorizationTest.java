package qa.guru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.rococo.jupiter.annotation.TestUser;
import qa.guru.rococo.jupiter.annotation.User;
import qa.guru.rococo.model.UserJson;
import qa.guru.rococo.po.LoginPage;
import qa.guru.rococo.po.MainPage;
import qa.guru.rococo.po.WelcomePage;

public class AuthorizationTest extends BaseWebTest {

    @DisplayName("WEB User should authorize with valid credentials")
    @TestUser
    @Test
    void loginTest(@User(User.Point.OUTER) UserJson user) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .waitForPageLoaded()
                .doLogin()
                .waitForPageLoaded()
                .fillLoginPage(user.username(), user.testData().password())
                .submit(new MainPage())
                .waitForPageLoaded()
                .avatarShouldBeVisibleAfterLogin();
    }


    @DisplayName("WEB User should not authorize with incorrect credentials")
    @TestUser(fake = true)
    @Test
    void incorrectUserLoginTest() {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .waitForPageLoaded()
                .doLogin()
                .waitForPageLoaded()
                .fillLoginPage("kirill", "wrongPassword")
                .submit(new LoginPage())
                .checkError("Неверные учетные данные пользователя");
    }
}
