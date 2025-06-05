package ru.hulumulumulus.lab3;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthTest extends FlRuTest {
    @Test
    void testLogin() {
        login();
    }

    private static final String TEMP_EMAIL = "makhia0@lopvede.com";

    @Test
    void testRegister() {
        driver.get("https://www.fl.ru");

        // Кликаем регистрацию
        WebElement mainLoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-id=\"qa-head-registration\"]")));
        mainLoginButton.click();

        // Ждем кнопку "Продолжить как фрилансер"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='btn btn-primary text-nowrap mt-24 w-100' and contains(text(), 'Продолжить как фрилансер')]")
        )).click();

        // Кликаем все галочки с помощью JavaScript
        WebElement firstCheckboxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ui-checkbox-rules.rule_1']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstCheckboxInput);

        WebElement secondCheckboxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ui-checkbox-rules.rule_3']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", secondCheckboxInput);


        // Вводим логин
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='ui-input-user-email']")
        ));
        usernameField.sendKeys(TEMP_EMAIL); // temp email

        // Вводим пароль
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='user-password']")
        ));
        passwordField.sendKeys(TEST_PASSWORD);

        // Ждем капчу
        WebElement captchaIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@data-testid='checkbox-iframe']")));
        driver.switchTo().frame(captchaIframe);

        waitForElementByXpath(driver, "//div[@class='CheckboxCaptcha-Checkbox' and @data-checked='true']");

        driver.switchTo().defaultContent();

        // Кликаем регистрацию
        WebElement modalLoginSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='qa-registration-button']")
        ));
        modalLoginSubmitButton.click();

        // Закрываем модальное окно "Получайте новые заказы в Telegram"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'Подключу потом')]")
        )).click();

        // Ждем, пока модальное окно Telegram исчезнет
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("prj-to-tg-modal")));

        // Кликаем уровень экспертизы "Без опыта"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'fl-level-box') and .//div[normalize-space(text())='Без опыта']]")
        )).click();

        // Кликаем продолжить
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='wizard-btn btn btn-primary w-100 text-nowrap mb-48' and normalize-space(text())='Продолжить']")
        )).click();

        // Вводим информацию о себе
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='firstName']")
        )).sendKeys("Иван");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='surname']")
        )).sendKeys("Иванов");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//textarea[@id='aboutMe']")
        )).sendKeys("Я третья лабораторная работа по тестированию программного обеспечения, " +
                "специализируюсь на восточных мудростях таких как битва с драконами, поедание " +
                "арабских хачапури и звездные войны. Мой главный учитель Клименков Сергей Викторович, дай бог ему здоровья.");

        // Выбор страны Россия
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='countries']//div[contains(@class, 'vs__dropdown-toggle')]")
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='countries']//input[contains(@class, 'vs__search')]")
        )).sendKeys("Россия");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul[@id='vs1__listbox']/li[normalize-space()='Россия']")
        )).click();

        // Выбор города Санкт-Петербург
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='cities']//div[contains(@class, 'vs__dropdown-toggle')]")
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='cities']//input[contains(@class, 'vs__search')]")
        )).sendKeys("Санкт-Петербург");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul[@id='vs2__listbox']/li[normalize-space()='Санкт-Петербург']")
        )).click();

        // Кликаем продолжить
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='wizard-btn btn btn-primary w-100 text-nowrap mb-48' and normalize-space(text())='Продолжить']")
        )).click();

        // Выбираем категорию "Программирование"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@for='post-5']") // Click to expand main category "Программирование"
        )).click();

        // Выбираем подкатегорию "Веб-программирование"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@for='ui-radio-prof_group-37']") // Click sub-category "Веб-программирование"
        )).click();

        // Пикаем навык пивоварение
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='select-skills']//div[contains(@class, 'vs__dropdown-toggle')]")
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='select-skills']//input[contains(@class, 'vs__search')]")
        )).sendKeys("Пивоварение");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul[@id='vs3__listbox']/li[normalize-space()='Пивоварение']")
        )).click();

        // Зарегистрироваться
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='wizard-btn btn btn-primary mb-120' and normalize-space(text())='Зарегистрироваться']")
        )).click();

        // Проверяем, что удалось залогиниться
        boolean isLoggedIn = false;
        try {
            WebElement loginSuccessIndicator = waitForElementByXpath(driver, LOGIN_SUCCESS_INDICATOR_XPATH);
            isLoggedIn = loginSuccessIndicator.isDisplayed();
        } catch (Exception ignored) {
        }

        assertTrue(isLoggedIn, "Вход на сайт не выполнен");
    }
}
