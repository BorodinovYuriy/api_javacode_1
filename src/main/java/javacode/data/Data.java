package javacode.data;


import com.github.javafaker.Faker;

public class Data {
    static Faker faker = new Faker();

    public static String makeUsername() {
        return faker.name().username();
    }

    public static String makePassword() {
        return faker.internet().password();
    }

    public static String makeName() {
        return faker.name().name();
    }

    public static String makeSurname() {
        return faker.name().lastName();
    }
    public static String makeEmail() {
        return faker.internet().emailAddress();
    }
}