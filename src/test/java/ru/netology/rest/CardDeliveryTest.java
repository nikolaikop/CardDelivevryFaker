package ru.netology.rest;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    void dataInput(int days) {
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(UserData.dateData(days));
    }

    @BeforeEach
    void before() {
        open("http://localhost:9999");
    }

    @Test
    void ifDataIsGood() {
        $("[placeholder=Город]").setValue(UserData.randomCityForInput());
        int inDays = 4;
        dataInput(inDays);
        $("[data-test-id=phone]").$("[name=phone]").setValue(UserData.phoneData());
        $("[data-test-id=name].input_type_text .input__control").setValue(UserData.nameData());
        $("[class=checkbox__box]").click();
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").$("[class=notification__content]")
                .shouldHave(textCaseSensitive("Встреча успешно запланирована на " + UserData.dateData(inDays)));
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $$("[class=button__text]").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").$("[class=notification__content]")
                .shouldHave(textCaseSensitive("Встреча успешно запланирована на " + UserData.dateData(inDays)));
    }

    @Test
    void ifRandomNames() {
        $("[placeholder=Город]").setValue(UserData.randomCityForInput());
        int inDays = 4;
        dataInput(inDays);
        $("[data-test-id=phone]").$("[name=phone]").setValue(UserData.phoneData());
        $("[data-test-id=name].input_type_text .input__control").setValue(UserData.randomNamesForInput());
        $("[class=checkbox__box]").click();
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").$("[class=notification__content]")
                .shouldHave(textCaseSensitive("Встреча успешно запланирована на " + UserData.dateData(inDays)));
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $$("[class=button__text]").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").$("[class=notification__content]")
                .shouldHave(textCaseSensitive("Встреча успешно запланирована на " + UserData.dateData(inDays)));
    }

    @Test
    void errorIfNoDate() {
        $("[placeholder=Город]").setValue(UserData.randomCityForInput());
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=phone]").$("[name=phone]").setValue(UserData.phoneData());
        $("[data-test-id=name].input_type_text .input__control").setValue(UserData.nameData());
        $("[class=checkbox__box]").click();
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input__sub").shouldHave
                (exactTextCaseSensitive("Неверно введена дата"));
    }

    @Test
    void ifCityIsBad() {
        $("[placeholder=Город]").setValue(UserData.badCities());
        int inDays = 4;
        dataInput(inDays);
        $("[data-test-id=phone]").$("[name=phone]").setValue(UserData.phoneData());
        $("[data-test-id=name].input_type_text .input__control").setValue(UserData.nameData());
        $("[class=checkbox__box]").click();
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $("[data-test-id=city] .input__sub").shouldHave
                (exactTextCaseSensitive("Доставка в выбранный город недоступна"));
    }
}
