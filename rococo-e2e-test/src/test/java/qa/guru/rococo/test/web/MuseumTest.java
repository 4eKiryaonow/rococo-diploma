package qa.guru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.rococo.jupiter.annotation.ApiLogin;
import qa.guru.rococo.jupiter.annotation.GenerateMuseum;
import qa.guru.rococo.jupiter.annotation.TestUser;
import qa.guru.rococo.model.MuseumJson;
import qa.guru.rococo.po.MuseumPage;
import qa.guru.rococo.utils.RandomGenerator;

import java.util.List;

public class MuseumTest extends BaseWebTest {

    @DisplayName("WEB Museums are available for log out user")
    @Test
    @GenerateMuseum(count = 3)
    void unauthorizedUserShouldSeeMuseumsTest(List<MuseumJson> museumJsonList) {
        Selenide.open(MuseumPage.URL, MuseumPage.class)
                .waitForPageLoaded()
                .checkAddMuseumButton(false)
                .checkMuseums(museumJsonList);
    }

    @DisplayName("WEB Museums are available for log in user")
    @Test
    @ApiLogin(user = @TestUser())
    @GenerateMuseum(count = 3)
    void authorizedUserShouldSeeMuseumsTest(List<MuseumJson> museumJsonList) {
        Selenide.open(MuseumPage.URL, MuseumPage.class)
                .waitForPageLoaded()
                .checkAddMuseumButton(true)
                .checkMuseums(museumJsonList);
    }

    @DisplayName("WEB Add museum being authorized user")
    @Test
    @ApiLogin(user = @TestUser())
    void addMuseumTest() {
        MuseumJson museumJson = RandomGenerator.generateMuseum();
        Selenide.open(MuseumPage.URL, MuseumPage.class)
                .waitForPageLoaded()
                .addMuseum(
                        museumJson.title(),
                        museumJson.description(),
                        museumJson.geo().country().name(),
                        museumJson.geo().name(),
                        RandomGenerator.getMuseumImage())
                .checkAlertMessage(museumJson.title())
                .checkMuseum(museumJson);
    }

    @DisplayName("WEB Edit museum being authorized user")
    @Test
    @ApiLogin(user = @TestUser())
    @GenerateMuseum()
    void editMuseumTest(List<MuseumJson> museumJsonList) {
        MuseumJson museumJson = RandomGenerator.generateMuseum();
        String currentTitle = museumJsonList.getFirst().title();
        Selenide.open(MuseumPage.URL, MuseumPage.class)
                .waitForPageLoaded()
                .openMuseum(currentTitle)
                .waitForPageLoaded()
                .editMuseum(
                        museumJson.title(),
                        museumJson.description(),
                        museumJson.geo().country().name(),
                        museumJson.geo().name(),
                        RandomGenerator.getMuseumImage())
                .checkAlertMessage("Обновлен музей: " + museumJson.title());
    }
}
