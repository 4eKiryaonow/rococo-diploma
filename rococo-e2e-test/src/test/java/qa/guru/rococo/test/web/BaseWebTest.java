package qa.guru.rococo.test.web;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import qa.guru.rococo.jupiter.annotation.WebTest;

@WebTest
public abstract class BaseWebTest {

    static {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito", "--start-maximized");
        Configuration.browserCapabilities = options;
    }
}
