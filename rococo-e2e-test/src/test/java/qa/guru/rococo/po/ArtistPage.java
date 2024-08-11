package qa.guru.rococo.po;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import qa.guru.rococo.model.ArtistJson;
import qa.guru.rococo.po.component.AddArtistModal;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ArtistPage extends BasePage<ArtistPage> {
    public static final String URL = CFG.frontUrl() + "/artist";

    private final SelenideElement addArtistBtn = $$("button").find(Condition.text("Добавить художника"));
    private final SelenideElement pageTitle = $$("h2").find(Condition.text("Художники"));
    private final ElementsCollection artistsCards = $$(".flex.flex-col.justify-center.items-center");

    @Override
    public ArtistPage waitForPageLoaded() {
        progressRadialShouldNotBeVisible();
        pageTitle.should(visible);
        searchInput.should(visible);
        searchSubmitBtn.should(visible);
        return this;
    }

    public <T extends BasePage> T checkButtonAddArtist(T expectedPage, boolean isAvailable) {
        if (isAvailable) {
            addArtistBtn.should(visible);
        } else {
            addArtistBtn.shouldNotBe(visible);
        }
        return expectedPage;
    }

    public AddArtistModal addArtist() {
        addArtistBtn.click();
        return new AddArtistModal($(".card.p-4.w-modal.shadow-xl.space-y-4"));
    }

    public ArtistProfilePage selectArtist(String artistName) {
        searchInput.setValue(artistName);
        searchSubmitBtn.click();
        artistsCards.find(text(artistName)).click();
        progressRadialShouldNotBeVisible();
        return new ArtistProfilePage();
    }

    public ArtistPage searchArtist(List<ArtistJson> artistJsons) {
        for (var artist : artistJsons) {
            searchInput.setValue(artist.name());
            searchSubmitBtn.click();
            artistsCards.find(text(artist.name())).should(visible);
            progressRadialShouldNotBeVisible();
        }
        return this;
    }

}
