package qa.guru.rococo.po.component;

import com.codeborne.selenide.SelenideElement;
import qa.guru.rococo.model.ArtistJson;
import qa.guru.rococo.po.BasePage;

import javax.annotation.Nonnull;
import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class AddArtistModal extends BaseComponent {

    private final SelenideElement title = self.$("header");
    private final SelenideElement artistNameInput = self.$("input[name='name']");
    private final SelenideElement updatePhotoInput = self.$("input[name='photo']");
    private final SelenideElement biographyInput = self.$("textarea[name='biography']");
    private final SelenideElement closeBtn = self.$(".btn.variant-ringed");
    private final SelenideElement submitBtn = self.$("button[type='submit']");
    private final SelenideElement avatarImage = self.$(".avatar-image"); //только при редактировании художника


    public AddArtistModal(@Nonnull SelenideElement self) {
        super(self);
    }

    public AddArtistModal waitForModalLoaded() {
        title.should(text("Новый художник"), Duration.ofSeconds(2000));
        closeBtn.should(visible);
        submitBtn.should(visible);
        return this;
    }

    public AddArtistModal fillArtistInfo(ArtistJson artistJson, File file) {
        artistNameInput.setValue(artistJson.name());
        biographyInput.setValue(artistJson.biography());
        updatePhotoInput.uploadFile(file);
        return this;
    }

    public <T extends BasePage> T submit(T expectedPage) {
        submitBtn.click();
        return expectedPage;
    }

}
