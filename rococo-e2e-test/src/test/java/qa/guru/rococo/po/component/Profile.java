package qa.guru.rococo.po.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import qa.guru.rococo.po.BasePage;
import qa.guru.rococo.utils.RandomGenerator;

import javax.annotation.Nonnull;

import java.io.File;

import static com.codeborne.selenide.Condition.visible;

@Getter
public class Profile extends BaseComponent<Profile> {

    private final SelenideElement title = self.$("header");
    private final SelenideElement avatarImage = self.$(".avatar-image");
    private final SelenideElement username = self.$("h4.text-center");
    private final SelenideElement exitBtn = self.$(".btn.variant-ghost");
    private final SelenideElement closeBtn = self.$(".btn.variant-ringed");
    private final SelenideElement submitBtn = self.$("button[type='submit']");
    private final SelenideElement updatePhotoInput = self.$("input[name='content']");
    private final SelenideElement firstnameInput = self.$("input[name='firstname']");
    private final SelenideElement surnameInput = self.$("input[name='surname']");


    public Profile(@Nonnull SelenideElement self) {
        super(self);
    }

    public Profile fillFirstname(String firstname) {
        firstnameInput.setValue(firstname);
        return this;
    }

    public Profile fillSurname(String surname) {
        surnameInput.setValue(surname);
        return this;
    }

    public Profile uploadAvatar() {
        File file = new File(RandomGenerator.getProfileImage().getAbsolutePath());
        updatePhotoInput.uploadFile(file);
        return this;
    }

    public Profile checkAvatar() {
        avatarImage.should(visible);
        return this;
    }

    public <T extends BasePage> T submitForm(T expectedPage) {
        submitBtn.click();
        return expectedPage;
    }

    public Profile checkFirstname(String firstname) {
        return this;
    }

    public Profile checkSurname(String surname) {
        return this;
    }

    public Profile checkUsername(String username) {
        this.username.should(Condition.text(username));
        return this;
    }


}
