package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import tests.helpers.Attach;
import tests.helpers.DriverSettings;

@ExtendWith({AllureJunit5.class})
public class TestBase {
    static TestData testData = new TestData();
    static NewUserVerificationData newUserVerificationData = new NewUserVerificationData();

    @BeforeAll
    static void beforeAll() {
        DriverSettings.configure();
//        Configuration.baseUrl = "http://demowebshop.tricentis.com";
//        RestAssured.baseURI = "http://demowebshop.tricentis.com";

//        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("enableVNC", true);
//        capabilities.setCapability("enableVideo", true);
//        Configuration.remote = format("https://%s:%s@%s",
//                testData.email, testData.passwordRnd, testData.remoteUrlSelenoid);
    }

    @BeforeEach
    public void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

//
    @AfterEach
    void addAttaches() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

        Selenide.closeWebDriver();
    }
}
