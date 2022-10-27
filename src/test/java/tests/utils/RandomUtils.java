package tests.utils;

import com.github.javafaker.Faker;

public class RandomUtils {

    public static String getRandomGender() {
        Faker faker = new Faker();

        String gender = faker.demographic().sex();
        String female = "Female";

        if (gender == female)
            gender = "F";
        return gender;
    }
}
