package qa.guru.rococo.po;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import qa.guru.rococo.model.MuseumJson;
import qa.guru.rococo.po.component.MuseumModal;

import java.io.File;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MuseumPage extends BasePage<MuseumPage> {

    public static final String URL = CFG.frontUrl() + "museum";

    private final MuseumModal museumModal = new MuseumModal($(".card.p-4.w-modal.shadow-xl.space-y-4"));

    private final SelenideElement addMuseumBtn = $$("button").find(Condition.text("Добавить музей"));
    private final SelenideElement pageTitle = $$("h2").find(Condition.text("Музеи"));

    private final ElementsCollection museumsCards = $$("a[href*='/museum/']");


    @Override
    public MuseumPage waitForPageLoaded() {
        progressRadialShouldNotBeVisible();
        pageTitle.should(visible);
        searchInput.should(visible);
        searchSubmitBtn.should(visible);
        return this;
    }

    @Override
    public MuseumPage findByTitle(String title) {
        return super.findByTitle(title);
    }

    public MuseumContentPage openMuseum(String title) {
        findByTitle(title);
        museumsCards.find(text(title)).click();
        return new MuseumContentPage();
    }

    public MuseumPage addMuseum(String title, String description, String country, String city, File image) {
        addMuseumBtn.click();
        museumModal.fillMuseumInformation(title, description, country, city, image);
        museumModal.submitMuseum();
        return this;
    }

    public MuseumPage checkMuseums(List<MuseumJson> museumJsons) {
        museumsCards.should(sizeGreaterThanOrEqual(museumJsons.size()));
        for (var museum : museumJsons) {
            checkMuseum(museum);
        }
        return this;
    }

    public MuseumPage checkMuseum(MuseumJson museum) {
        String title = museum.title();
        String city = museum.geo().name();
        String name = museum.geo().country().name();
        searchInput.setValue(title);
        searchSubmitBtn.click();
        museumsCards.find(text(title)).should(visible);
        museumsCards.find(text(city)).should(visible);
        museumsCards.find(text(name)).should(visible);
        return this;
    }

    public MuseumPage checkAddMuseumButton(boolean isAvailable) {
        if (isAvailable) {
            addMuseumBtn.should(visible);
        } else {
            addMuseumBtn.shouldNotBe(visible);
        }
        return this;
    }
}
