package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Cookie;
import tests.helpers.AllureRestAssuredFilter;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static tests.helpers.AllureRestAssuredFilter.withCustomTemplates;

public class RegistrationTest extends TestBase {

    @Test
    @Tag("demowebshop2")
    void addToNewCartAsAnonymTest() {

        step("Регистрируем нового пользователя", () -> {
            given()
                    .filter(AllureRestAssuredFilter.withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .cookie(newUserVerificationData.requestVerificationTokenName,
                            newUserVerificationData.requestVerificationTokenValue)
                    .formParam(newUserVerificationData.requestVerificationTokenName,
                            newUserVerificationData.requestVerificationTokenParamValue)
                    .formParam("Gender", testData.gender)
                    .formParam("FirstName", testData.firstName)
                    .formParam("LastName", testData.lastName)
                    .formParam("Email", testData.email)
                    .formParam("Password", testData.passwordRnd)
                    .formParam("ConfirmPassword", testData.passwordRnd)
                    .log().all()
                    .when()
                    .post("/register")
                    .then()
                    .log().all()
                    .statusCode(302);
        });
    }

    @Test
    @Tag("demowebshop2")
    void loginWithCookieTest2() {

        step("Get cookie by api and set it to browser", () -> {
            String authCookieValue =
                    given()
                            .filter(AllureRestAssuredFilter.withCustomTemplates())
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .formParam("Email", testData.email)
                            .formParam("Password", testData.passwordRnd)
                            .log().all()
                            .when()
                            .post("/login")
                            .then()
                            .log().all()
                            .statusCode(302)
                            .extract()
                            .cookie(testData.authCookieName);

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));

            step("Set cookie to to browser", () ->
                    getWebDriver().manage().addCookie(new Cookie(testData.authCookieName, authCookieValue)));

            step("Редактируем Имя и Фамилию через REST API", () -> {

                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .filter(withCustomTemplates())
                        .cookie(testData.authCookieName, authCookieValue)
                        .cookie(newUserVerificationData.requestVerificationTokenName,
                                newUserVerificationData.requestVerificationTokenValue)
                        .formParam(newUserVerificationData.requestVerificationTokenName,
                                newUserVerificationData.requestVerificationTokenParamValue)
                        .formParam("FirstName", "Ivan")
                        .formParam("LastName", "Ivanov")
                        .formParam("Email", "G" + testData.email)
                        .log().all()
                        .when()
                        .post("http://demowebshop.tricentis.com/customer/info")
                        .then()
                        .log().all()
                        .statusCode(302);

                step("Открываем страницу редактирования данных пользователя", () -> {
                    open("http://demowebshop.tricentis.com/customer/info");
                });
            });

            step("Проверяем результат работы через Web", () -> {
                $("#FirstName").shouldHave(attribute("value", "Ivan"));
                $("#LastName").shouldHave(attribute("value", "Ivanov"));
                $("#Email").shouldHave(attribute("value", "G" + testData.email));
            });
        });
    }
}