package qa.guru.rococo.po;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage> {

    public static final String URL = CFG.authUrl() + "/login";

    SelenideElement
            header = $(".form__header"),
            usernameInput = $("input[name='username']"),
            passwordInput = $("input[name='password']"),
            submitButton = $(".form__submit"),
            errorContainer = $(".form__error");

    public LoginPage fillLoginPage(String login, String password) {
        setUsername(login);
        setPassword(password);
        return this;
    }

    public LoginPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public <T extends BasePage> T submit(T expectedPage) {
        submitButton.click();
        return expectedPage;
    }

    public LoginPage checkError(String error) {
        errorContainer.shouldHave(text(error));
        return this;
    }

    @Override
    public LoginPage waitForPageLoaded() {
        header.shouldHave(text("Ro"));
        header.$(".form__text_gold").shouldHave(text("coco"));
        usernameInput.should(visible);
        passwordInput.should(visible);
        submitButton.should(visible).shouldHave(text("Войти"));

        return this;
    }
}
