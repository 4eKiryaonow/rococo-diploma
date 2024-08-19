package qa.guru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.rococo.jupiter.annotation.ApiLogin;
import qa.guru.rococo.jupiter.annotation.GeneratePainting;
import qa.guru.rococo.jupiter.annotation.TestUser;
import qa.guru.rococo.model.PaintingJson;
import qa.guru.rococo.po.PaintingPage;

import java.util.List;

public class PaintingTest extends BaseWebTest {

    @DisplayName("WEB Paintings are available for unauthorized user")
    @Test
    @GeneratePainting(count = 3)
    void unauthorizedUserShouldSeePaintingsTest(List<PaintingJson> paintingJsons) {
        Selenide.open(PaintingPage.URL, PaintingPage.class)
                .waitForPageLoaded()
                .checkAddPaintingButton(false)
                .checkPaintings(paintingJsons);
    }

    @DisplayName("WEB Paintings are available for authorized user")
    @Test
    @ApiLogin(user = @TestUser())
    @GeneratePainting(count = 3)
    void authorizedUserShouldSeePaintingsTest(List<PaintingJson> paintingJsons) {
        Selenide.open(PaintingPage.URL, PaintingPage.class)
                .waitForPageLoaded()
                .checkAddPaintingButton(true)
                .checkPaintings(paintingJsons);
    }
}
