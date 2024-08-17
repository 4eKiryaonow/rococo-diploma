package qa.guru.rococo.po.component;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import qa.guru.rococo.po.ArtistPage;
import qa.guru.rococo.po.MuseumPage;
import qa.guru.rococo.po.PaintingPage;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Getter
public class Header extends BaseComponent<Header> {

    private final SelenideElement paintingButton = self.$("a[href*='/painting']");
    private final SelenideElement artistButton = self.$("a[href*='/artist']");
    private final SelenideElement museumButton = self.$("a[href*='/museum']");
    private final SelenideElement loginButton = self.$$("button").find(text("Войти"));
    private final SelenideElement avatar = self.$(".avatar");

    public Header(@Nonnull SelenideElement self) {
        super(self);
    }

    @Nonnull
    public PaintingPage goToPaintingPage() {
        paintingButton.click();
        return new PaintingPage();
    }


    @Nonnull
    public ArtistPage goToArtistPage() {
        artistButton.click();
        return new ArtistPage();
    }

    @Nonnull
    public MuseumPage goToMuseumPage() {
        museumButton.click();
        return new MuseumPage();
    }


    @Nonnull
    public Profile goToProfile() {
        avatar.click();
        return new Profile($(".card.p-4.w-modal.shadow-xl.space-y-4"));
    }

    public void checkLoginButton(){
        loginButton.shouldBe(visible);
    }
}
