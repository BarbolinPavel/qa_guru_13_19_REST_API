package tests;

import com.github.javafaker.Faker;

import static tests.utils.RandomUtils.getRandomGender;

public class TestData {
//    static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
//    String login = config.login();
//    String password = config.password();
//    String remoteUrlSelenoid = config.remoteUrlSelenoid();


    String gender = getRandomGender();

    String authCookieName  = "NOPCOMMERCE.AUTH";
    String authCookieValue;
    Faker faker = new Faker();
    String firstName = faker.funnyName().name(),
            lastName = faker.name().lastName(),
            email = faker.internet().safeEmailAddress(),
            passwordRnd = faker.internet().password();
}