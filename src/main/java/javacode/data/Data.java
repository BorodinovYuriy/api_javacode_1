package javacode.data;


import com.github.javafaker.Faker;

public class Data {
    static Faker faker = new Faker();

    public static String makeUsername(){
        return faker.name().username();
    }
    public static String makePassword(){
        return faker.internet().password();
    }
    public static CharSequence makeName() {
        return faker.name().name();
    }
    public static CharSequence makeCountry() {
        return faker.address().country();
    }
    public static CharSequence makeCity() {
        return faker.address().city();
    }
    public static CharSequence makeCard() {
        return faker.number().digits(16);
    }
    public static CharSequence makeMonth() {
        return faker.number().digits(1);
    }
    public static CharSequence makeYear() {
        return faker.number().digits(4);
    }
}
