package qa.guru.rococo.po.component;

import com.codeborne.selenide.SelenideElement;
import qa.guru.rococo.model.PaintingJson;
import qa.guru.rococo.po.BasePage;

import javax.annotation.Nonnull;
import java.io.File;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static java.time.Duration.ofSeconds;
import static qa.guru.rococo.utils.RandomGenerator.generateRandomInt;

public class AddPaintingModal extends BaseComponent {
    private final SelenideElement paintingNameInput = self.$("input[name='title']");
    private final SelenideElement paintingNameLabel = self.$$(".label").find(text("Название картины"));
    private final SelenideElement paintingInput = self.$("input[name='content']");
    private final SelenideElement descriptionTextarea = self.$("textarea[name='description']");
    private final SelenideElement authorSelect = self.$("select[name='authorId']");
    private final SelenideElement museumSelect = self.$("select[name='museumId']");
    private final SelenideElement closeBtn = self.$(".btn.variant-ringed");
    private final SelenideElement submitBtn = self.$("button[type='submit']");

    public AddPaintingModal(@Nonnull SelenideElement self) {
        super(self);
    }


    public AddPaintingModal fillPaintingInfo(PaintingJson paintingJson, File file) {
        paintingNameInput.setValue(paintingJson.title());
        paintingInput.uploadFile(file);
        descriptionTextarea.setValue(paintingJson.description());
        museumSelect.$$("option").shouldHave(sizeGreaterThan(0), ofSeconds(3000));
        int size = museumSelect.$$("option").size();
        museumSelect.$$("option").get(generateRandomInt(0, size - 1)).click();
        if (authorSelect.is(visible)) {
            authorSelect.$$("option").shouldHave(sizeGreaterThan(0), ofSeconds(3000));
            int authorsQty = authorSelect.$$("option").size();
            authorSelect.$$("option").get(generateRandomInt(0, authorsQty - 1)).click();
        }
        return this;
    }

    public AddPaintingModal waitForModalLoaded() {
        paintingNameLabel.should(visible, ofSeconds(2000));
        closeBtn.should(visible);
        submitBtn.should(visible);
        return this;
    }

    public <T extends BasePage> T submit(T expectedPage) {
        submitBtn.click();
        return expectedPage;
    }


}
