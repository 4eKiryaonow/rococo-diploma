package qa.guru.rococo.po;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import qa.guru.rococo.model.ArtistJson;
import qa.guru.rococo.model.PaintingJson;
import qa.guru.rococo.po.component.AddPaintingModal;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;

public class ArtistProfilePage extends BasePage<ArtistProfilePage> {

    private final SelenideElement artistName = $(".card-header");
    private final SelenideElement biography = $(".col-span-2.m-2");
    private final SelenideElement avatarImage = $(".avatar-image");
    private final SelenideElement artistPictures = $(".grid.grid-cols-1.gap-4");
    private final SelenideElement emptyPicturesListMessage = $(new ByText("Пока что список картин этого художника пуст."));

    //for authorized user only
    private final SelenideElement editArtistBtn = $("button[data-testid='edit-artist']");
    private final SelenideElement addPictureMainBtn = $(".btn.variant-filled-primary.m-3");
    private final SelenideElement addPictureSecondBtn = $(".btn.variant-filled-primary.ml-4");


    @Override
    public ArtistProfilePage waitForPageLoaded() {
        progressRadialShouldNotBeVisible();
        artistName.should(visible, ofSeconds(3000));
        biography.should(visible, ofSeconds(3000));
        return this;
    }

    public AddPaintingModal clickAddPaintingBtn() {
        addPictureMainBtn.click();
        return new AddPaintingModal($(".card.p-4.w-modal.shadow-xl.space-y-4"));
    }

    public ArtistProfilePage checkThatPaintingIsEnableOnArtistProfilePage(PaintingJson paintingJson) {
        artistPictures.$$(".text-center").find(text(paintingJson.title())).should(visible);
        return this;
    }

    public ArtistProfilePage checkArtistInfo(ArtistJson artistJson) {
        artistName.should(text(artistJson.name()));
        biography.should(text(artistJson.biography()));
        return this;
    }
}
