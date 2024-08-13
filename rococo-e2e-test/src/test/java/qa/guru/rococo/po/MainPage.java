package qa.guru.rococo.po;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import qa.guru.rococo.po.component.Header;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Getter
public class MainPage extends BasePage<MainPage> {

    public static final String URL = CFG.frontUrl();

    private final Header header = new Header($("#shell-header"));
    private final SelenideElement welcomeTitle = $(".text-3xl.text-center.m-14");
    private final SelenideElement paintingCard = $("a[href='/painting']");
    private final SelenideElement artistsCard = $("a[href='/artist']");
    private final SelenideElement museumsCard = $("a[href='/museum']");

    @Override
    public MainPage waitForPageLoaded() {
        alertMessage.shouldNotBe(visible);
        welcomeTitle.should(text("Ваши любимые картины и художники всегда рядом"));
        paintingCard.should(visible);
        artistsCard.should(visible);
        museumsCard.should(visible);
        return this;
    }

    public MainPage avatarShouldBeVisibleAfterLogin() {
        header.getAvatar().should(visible);
        return this;
    }
}
