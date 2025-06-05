package ru.hulumulumulus.lab3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class AuthTest extends FlRuTest {
    @Test
    void testLogin() {
        login();
    }

    private static final String TEMP_EMAIL = "makhia0@lopvede.com";

    @Test
    void testRegister() {
        driver.get("https://www.fl.ru");

        // Нажимаем "Регистрация"
        WebElement mainLoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-id=\"qa-head-registration\"]")));
        mainLoginButton.click();

        // Ждем кнопку "Продолжить как фрилансер" и нажимаем
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='btn btn-primary text-nowrap mt-24 w-100' and contains(text(), 'Продолжить как фрилансер')]")
        )).click();

        // Принимаем условия (кликаем чекбоксы)
        WebElement firstCheckboxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ui-checkbox-rules.rule_1']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstCheckboxInput);

        WebElement secondCheckboxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ui-checkbox-rules.rule_3']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", secondCheckboxInput);


        // Вводим email
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='ui-input-user-email']")
        ));
        usernameField.sendKeys(TEMP_EMAIL); // Временный email для регистрации

        // Вводим пароль
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='user-password']")
        ));
        passwordField.sendKeys(TEST_PASSWORD);

        // Ожидаем и проходим капчу
        WebElement captchaIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@data-testid='checkbox-iframe']")));
        driver.switchTo().frame(captchaIframe);

        waitForElementByXpath(driver, "//div[@class='CheckboxCaptcha-Checkbox' and @data-checked='true']");

        driver.switchTo().defaultContent();

        // Нажимаем "Зарегистрироваться" в модальном окне
        WebElement modalLoginSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='qa-registration-button']")
        ));
        modalLoginSubmitButton.click();

        // Закрываем предложение подключить Telegram
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'Подключу потом')]")
        )).click();

        // Ждем, пока исчезнет модальное окно Telegram
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("prj-to-tg-modal")));

        // Выбираем уровень "Без опыта"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'fl-level-box') and .//div[normalize-space(text())='Без опыта']]")
        )).click();

        // Нажимаем "Продолжить"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='wizard-btn btn btn-primary w-100 text-nowrap mb-48' and normalize-space(text())='Продолжить']")
        )).click();

        // Заполняем личные данные
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

        // Выбираем страну (Россия)
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='countries']//div[contains(@class, 'vs__dropdown-toggle')]")
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='countries']//input[contains(@class, 'vs__search')]")
        )).sendKeys("Россия");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul[@id='vs1__listbox']/li[normalize-space()='Россия']")
        )).click();

        // Выбираем город (Санкт-Петербург)
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='cities']//div[contains(@class, 'vs__dropdown-toggle')]")
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='cities']//input[contains(@class, 'vs__search')]")
        )).sendKeys("Санкт-Петербург");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul[@id='vs2__listbox']/li[normalize-space()='Санкт-Петербург']")
        )).click();

        // Снова "Продолжить"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='wizard-btn btn btn-primary w-100 text-nowrap mb-48' and normalize-space(text())='Продолжить']")
        )).click();

        // Выбираем категорию "Программирование"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@for='post-5']") // Раскрываем "Программирование"
        )).click();

        // Выбираем подкатегорию "Веб-программирование"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@for='ui-radio-prof_group-37']") // Кликаем "Веб-программирование"
        )).click();

        // Выбираем навык "Пивоварение"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='select-skills']//div[contains(@class, 'vs__dropdown-toggle')]")
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='select-skills']//input[contains(@class, 'vs__search')]")
        )).sendKeys("Пивоварение");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//ul[@id='vs3__listbox']/li[normalize-space()='Пивоварение']")
        )).click();

        // Финальная кнопка "Зарегистрироваться"
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='wizard-btn btn btn-primary mb-120' and normalize-space(text())='Зарегистрироваться']")
        )).click();

        // Проверяем успешность входа
        boolean isLoggedIn = false;
        try {
            WebElement loginSuccessIndicator = waitForElementByXpath(driver, LOGIN_SUCCESS_INDICATOR_XPATH);
            isLoggedIn = loginSuccessIndicator.isDisplayed();
        } catch (Exception ignored) {
        }

        assertTrue(isLoggedIn, "Вход на сайт не выполнен");
    }
}
