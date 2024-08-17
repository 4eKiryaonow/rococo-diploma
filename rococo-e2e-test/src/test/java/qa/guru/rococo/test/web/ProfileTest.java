package qa.guru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.rococo.jupiter.annotation.ApiLogin;
import qa.guru.rococo.jupiter.annotation.TestUser;
import qa.guru.rococo.jupiter.annotation.User;
import qa.guru.rococo.model.UserJson;
import qa.guru.rococo.po.MainPage;
import qa.guru.rococo.utils.RandomGenerator;


public class ProfileTest extends BaseWebTest {

    @Test
    @DisplayName("WEB Update user profile")
    @ApiLogin(user = @TestUser())
    void updateProfileDataTest(@User(User.Point.INNER) UserJson user) {
        String name = RandomGenerator.generateName();
        String surname = RandomGenerator.generateSurname();
        Selenide.open(MainPage.URL, MainPage.class)
                .waitForPageLoaded()
                .getHeader()
                .goToProfile()
                .checkUsername(user.username())
                .uploadAvatar()
                .fillFirstname(name)
                .fillSurname(surname)
                .submitForm(new MainPage())
                .checkAlertMessage("Профиль обновлен")
                .getHeader()
                .goToProfile()
                .checkFirstname(name)
                .checkSurname(surname)
                .checkAvatar();
    }

    @Test
    @DisplayName("WEB Logout from user profile")
    @ApiLogin(user = @TestUser())
    void logoutTest() {
        Selenide.open(MainPage.URL, MainPage.class)
                .waitForPageLoaded()
                .getHeader()
                .goToProfile()
                .logout(new MainPage())
                .checkAlertMessage("Сессия завершена")
                .getHeader()
                .checkLoginButton();
    }
}
