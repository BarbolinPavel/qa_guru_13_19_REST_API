package tests;

import com.github.javafaker.Faker;
import org.aeonbits.owner.ConfigFactory;
import tests.config.CredentialsConfig;

import static tests.utils.RandomUtils.getRandomGender;

public class TestData {
    CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    String login = config.login();
    String password = config.password();
    String remoteUrlSelenoid = config.remoteUrlSelenoid();

    String gender = getRandomGender();

    String authCookieName  = "NOPCOMMERCE.AUTH";
    Faker faker = new Faker();
    String firstName = faker.funnyName().name(),
            lastName = faker.name().lastName(),
            email = faker.internet().safeEmailAddress(),
            passwordR = faker.internet().password();
}