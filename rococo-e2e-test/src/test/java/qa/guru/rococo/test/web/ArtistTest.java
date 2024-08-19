package qa.guru.rococo.test.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.rococo.jupiter.annotation.ApiLogin;
import qa.guru.rococo.jupiter.annotation.GenerateArtist;
import qa.guru.rococo.jupiter.annotation.TestUser;
import qa.guru.rococo.model.ArtistJson;
import qa.guru.rococo.po.ArtistPage;
import qa.guru.rococo.utils.RandomGenerator;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;

public class ArtistTest extends BaseWebTest {

    @DisplayName("WEB Artists are available for log out user")
    @Test
    @GenerateArtist(count = 3)
    void unauthorizedUserShouldSeeArtistTest(List<ArtistJson> artistJsonList) {
        open(ArtistPage.URL, ArtistPage.class)
                .waitForPageLoaded()
                .searchArtist(artistJsonList);
    }

    @DisplayName("WEB Artists are available for log in user")
    @Test
    @ApiLogin(user = @TestUser())
    @GenerateArtist(count = 3)
    void authorizedUserShouldSeeArtistTest(List<ArtistJson> artistJsonList) {
        open(ArtistPage.URL, ArtistPage.class)
                .waitForPageLoaded()
                .searchArtist(artistJsonList);
    }

    @DisplayName("WEB Authorized user can add artist")
    @Test
    @ApiLogin(user = @TestUser())
    void authorizedUserCanAddArtist() {
        ArtistJson artistJson = RandomGenerator.generateArtist();
        open(ArtistPage.URL, ArtistPage.class)
                .waitForPageLoaded()
                .addArtist()
                .fillArtistInfo(artistJson, RandomGenerator.getArtistImage())
                .submit(new ArtistPage())
                .checkAlertMessage("Добавлен художник: " + artistJson.name())
                .selectArtist(artistJson.name())
                .waitForPageLoaded()
                .checkArtistInfo(artistJson);
    }

    @DisplayName("WEB Unauthorized user can't add artist")
    @Test
    void unauthorizedUserCanNotAddArtist() {
        open(ArtistPage.URL, ArtistPage.class)
                .waitForPageLoaded()
                .checkButtonAddArtist(new ArtistPage(), false);
    }
}
