package qa.guru.rococo.po;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import qa.guru.rococo.config.Config;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage<T extends BasePage<?>> {

    protected static final Config CFG = Config.getInstance();

    protected final SelenideElement alertMessage = $(".text-base");
    protected final SelenideElement progressRadial = $("figure[data-testid='progress-radial']");

    protected final SelenideElement searchInput = $("input[type='search']");
    protected final SelenideElement searchSubmitBtn = $(".btn-icon.variant-soft-surface.ml-4");

    public abstract T waitForPageLoaded();

    public T checkAlertMessage(String expectedText) {
        alertMessage.should(Condition.visible).should(Condition.text(expectedText));
        return (T) this;
    }
    public void progressRadialShouldNotBeVisible() {
        progressRadial.shouldNotBe(visible, Duration.ofSeconds(6000));
    }
}
