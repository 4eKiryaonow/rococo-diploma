package qa.guru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.rococo.jupiter.annotation.TestUser;
import qa.guru.rococo.jupiter.annotation.User;
import qa.guru.rococo.model.UserJson;
import qa.guru.rococo.po.MainPage;
import qa.guru.rococo.po.RegisterPage;

public class RegisterTest extends BaseWebTest {

    @DisplayName("WEB Registration User should login after registration")
    @Test
    @TestUser(fake = true)
    void userShouldLoginAfterRegistering(@User(User.Point.OUTER) UserJson user) {

        Selenide.open(RegisterPage.URL, RegisterPage.class)
                .waitForPageLoaded()
                .fillRegisterPage(user.username(), user.testData().password())
                .successSubmit()
                .waitForPageLoaded()
                .fillLoginPage(user.username(), user.testData().password())
                .submit(new MainPage())
                .waitForPageLoaded()
                .avatarShouldBeVisibleAfterLogin();
    }

    @DisplayName("WEB Registration should not be completed because password is different")
    @Test
    @TestUser(fake = true)
    void registrationShouldNotBeCompletedCausePasswordDiff(@User(User.Point.OUTER) UserJson user) {
        String diffPassword = user.testData().password() + "diff";
        Selenide.open(RegisterPage.URL, RegisterPage.class)
                .waitForPageLoaded()
                .setUsername(user.username())
                .setPassword(user.testData().password())
                .setPasswordSubmit(diffPassword)
                .errorSubmit()
                .checkErrorMessage("Passwords should be equal");
    }

    @DisplayName("WEB Registration should not be completed because password is short")
    @Test
    @TestUser(fake = true)
    void registrationShouldNotBeCompletedPasswordShort(@User(User.Point.OUTER) UserJson user) {
        String shortPassword = (user.testData().password()).substring(0,2);
        Selenide.open(RegisterPage.URL, RegisterPage.class)
                .waitForPageLoaded()
                .fillRegisterPage(user.username(), shortPassword)
                .errorSubmit()
                .checkErrorMessage("Allowed password length should be from 3 to 12 characters");
    }

    @DisplayName("WEB Registration should not be completed because password is too long")
    @Test
    @TestUser(fake = true)
    void registrationShouldNotBeCompletedPasswordLong(@User(User.Point.OUTER) UserJson user) {
        String longPassword = user.testData().password() + "add some length";
        Selenide.open(RegisterPage.URL, RegisterPage.class)
                .waitForPageLoaded()
                .fillRegisterPage(user.username(), longPassword)
                .errorSubmit()
                .checkErrorMessage("Allowed password length should be from 3 to 12 characters");
    }

    @DisplayName("WEB Registration should not be completed because user already exists")
    @Test
    @TestUser
    void registrationShouldNotBeCompletedUserAlreadyExists(@User(User.Point.OUTER) UserJson user) {
        Selenide.open(RegisterPage.URL, RegisterPage.class)
                .waitForPageLoaded()
                .fillRegisterPage(user.username(), user.testData().password())
                .errorSubmit()
                .checkErrorMessage("Username `" +  user.username() + "` already exists");
    }
}
