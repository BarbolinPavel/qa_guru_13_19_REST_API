package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Cookie;
import tests.helpers.AllureRestAssuredFilter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.Condition.text;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class DemoWebShopTest extends TestBase {

    @Test
    @Tag("demowebshop")
    @DisplayName("Successful registation to some demowebshop (API)")
    void registrationTest() {

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

            step("Открываем URL авторизации", () ->
                    open("/login"));
            step("Вводим логин и пароль в форму авторизации", () ->
                    $("#Email").setValue(testData.email));
                    $("#Password").setValue(testData.passwordRnd).pressEnter();

            step("Проверяем, что пользователь успешно залогинился", () ->
                    $(".account").shouldHave(text(testData.email)));

        });
    }

    @Test
    @Tag("demowebshop")
    @DisplayName("Successful authorization and change info to some demowebshop (API + UI)")
    void loginWithCookieTest2() {

        step("Авторизуем пользователя и передаем cookie", () -> {
            String authCookieValue =
                    given()
                            .filter(AllureRestAssuredFilter.withCustomTemplates())
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
//                            .cookie("ARRAffinity=92eb765899e80d8de4d490df907547e5cb10de899e8b754a4d5fa1a7122fad69")
                            .formParam("Email", testData.email)
                            .formParam("Password", testData.passwordRnd)
                            .formParam("RememberMe", "false")
                            .log().all()
                            .when()
                            .post("/login")
                            .then()
                            .log().all()
                            .statusCode(302)
                            .extract()
                            .cookie(testData.authCookieName);

            step("Oткрываем минимальный контент для передачи параметров cookie, " +
                    "чтобы открыть сайт авторизованным пользователем", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));

            step("Передаем cookie в браузер", () ->
                    getWebDriver().manage().addCookie(new Cookie(testData.authCookieName, authCookieValue)));

        });

        step("Редактируем Имя и Фамилию", () -> {

            step("Открываем страницу с данными пользователя", () -> {
                open("https://demowebshop.tricentis.com/customer/info");
            });

            step("Редактируем данные пользователя", () -> {
                $("#gender-male").click();
                $("#FirstName").setValue(testData.firstName);
                $("#LastName").setValue(testData.lastName);
                $("#Email").setValue("G" + testData.email);
                $(".save-customer-info-button").click();
            });
        });
        step("Открываем главную страницу", () -> {
            open("https://demowebshop.tricentis.com");
        });

        step("Проверяем изменение логина на главной странице", () -> {
            $(".account").shouldHave(text("G" + testData.email));
        });
    }
}
