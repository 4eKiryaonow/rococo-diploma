package qa.guru.rococo.po;

import com.codeborne.selenide.SelenideElement;
import qa.guru.rococo.po.component.MuseumModal;

import java.io.File;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MuseumContentPage extends BasePage<MuseumContentPage> {

    private final SelenideElement title = $(".card-header");
    private final SelenideElement geo = $(".text-center");
    private final SelenideElement photo = $("img.my-4");
    private final SelenideElement editButton = $("[data-testid=edit-museum]");
    private final MuseumModal museumModal = new MuseumModal($(".card.p-4.w-modal.shadow-xl.space-y-4"));


    @Override
    public MuseumContentPage waitForPageLoaded() {
        progressRadialShouldNotBeVisible();
        title.shouldBe(visible);
        geo.shouldBe(visible);
        photo.shouldBe(visible);
        return this;
    }

    public MuseumModal clickToEdit() {
        editButton.click();
        return this.museumModal;
    }

    public MuseumContentPage editMuseum(String title, String description, String country, String city, File image) {
        this.clickToEdit();
        museumModal.fillMuseumInformation(title, description, country, city, image);
        museumModal.submitMuseum();
        return this;
    }
}
