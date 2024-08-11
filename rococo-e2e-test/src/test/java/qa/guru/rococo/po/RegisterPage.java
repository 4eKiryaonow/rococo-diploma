package qa.guru.rococo.po;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage extends BasePage<RegisterPage> {

    public static final String URL = CFG.authUrl() + "register";

    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement passwordSubmitInput = $("input[name='passwordSubmit']");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement proceedLoginLink = $("a[href*='redirect']");
    private final SelenideElement errorContainer = $(".form__error");

    public RegisterPage fillRegisterPage(String login, String password) {
        setUsername(login);
        setPassword(password);
        setPasswordSubmit(password);
        return this;
    }

    public RegisterPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public RegisterPage setPasswordSubmit(String password) {
        passwordSubmitInput.setValue(password);
        return this;
    }

    public LoginPage successSubmit() {
        submitButton.click();
        proceedLoginLink.click();
        return new LoginPage();
    }

    public RegisterPage errorSubmit() {
        submitButton.click();
        return this;
    }

    @Override
    public RegisterPage waitForPageLoaded() {
        usernameInput.should(visible);
        passwordInput.should(visible);
        passwordSubmitInput.should(visible);
        return this;
    }

    public RegisterPage checkErrorMessage(String errorMessage) {
        errorContainer.shouldHave(text(errorMessage));
        return this;
    }
}
