package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import tests.helpers.Attach;

import static java.lang.String.format;

@ExtendWith({AllureJunit5.class})
public class TestBase {
    static TestData testData = new TestData();
    NewUserVerificationData newUserVerificationData = new NewUserVerificationData();

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.browserCapabilities = capabilities;
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
        Configuration.baseUrl = "http://demowebshop.tricentis.com";

        Configuration.browser = System.getProperty("browserName","chrome");
        Configuration.browserVersion = System.getProperty("version","99");
        Configuration.browserSize = System.getProperty("size", "1920x1080");
        Configuration.remote = format("https://%s:%s@%s",
                testData.login, testData.password, testData.remoteUrlSelenoid);

        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void addAttaches() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
