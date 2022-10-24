package tests.config;

import com.codeborne.selenide.Browser;
import org.aeonbits.owner.Config;

import java.net.URL;
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/local.properties",
        "classpath:config/remote.properties"
})

public interface ProjectConfig extends Config {

//    @Key("baseUrl")
//    @DefaultValue("http://demowebshop.tricentis.com")
//    String getBaseUrl();
//
//    @Key("browser")
//    @DefaultValue("chrome")
//    Browser getBrowser();
//
//    @Key("remoteUrl")
//    @DefaultValue("https://user1:1234@selenoid.autotests.cloud/wd/hub")
//    URL getSelenoidUrl();
//
//    @Key("versionBrowser")
//    @DefaultValue("100.0")
//    String getVersionBrowser();

    @DefaultValue("chrome")
    String browser();
    @DefaultValue("98.0")
    String browserVersion();
    @DefaultValue("1920x1080")
    String browserSize();
    String browserMobileView();
    String remoteDriverUrl();
    String videoStorage();
}

