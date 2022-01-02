package rest;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class UserData {

    static Faker faker = new Faker(new Locale("ru"));

    private UserData() {
    }

    static String randomCityForInput() {
        Random random = new Random();
        int rand = random.nextInt(10);
        String[] city = {"Санкт-Петербург", "Москва", "Хабаровск", "Амурск", "Комсомольск-на-Амуре", "Советская Гавань", "Новосибирск",
                "Челябинск", "Воркута", "Сыктывкар", "Пушкин", "Кронштадт", "Чебоксары"};
        return city[rand];
    }

    static String phoneData() {
        return faker.phoneNumber().phoneNumber();
    }

    static String nameData() {
        String name = faker.name().lastName();
        name = name + " " + faker.name().firstName();
        return name;
    }

    static String randomNamesForInput() {
        Random random = new Random();
        int rand = random.nextInt(7);
        String[] randomNames = {"Иванов Петр", "Пирожков Артур", "Сидовор Иван", "Петров Сидор",
                "Премудрая Василиса", "Печкин Почтальон"};
        return randomNames[rand];
    }

    static String dateData(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
    }

}
