package ru.hulumulumulus.lab3;

import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class FlRuTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected final boolean useChrome = false;

    protected final String TEST_USERNAME = "spare_dodder9m@icloud.com";
    protected final String TEST_PASSWORD = "i7ZAEc*5f%II";

    protected final String LOGIN_SUCCESS_INDICATOR_XPATH = "//a[@id='navbarRightDropdown' and @href='#' and @title='Меню профиля' and @data-id='qa-head-profile']";

    @BeforeEach
    void setUp() {
        if (useChrome) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            driver = new ChromeDriver(chromeOptions);
        } else {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            driver = new FirefoxDriver(firefoxOptions);
        }
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void login() {
        driver.get("https://www.fl.ru");

        // Нажимаем "Вход"
        WebElement mainLoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-id=\"qa-head-sign-in\"]")));
        mainLoginButton.click();

        // Вводим логин
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@name='username' and @type='text' and @placeholder='Логин, эл. почта или номер телефона']")
        ));
        usernameField.sendKeys(TEST_USERNAME);

        // Вводим пароль
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='password-field' and @name='password' and @type='password' and @placeholder='Пароль']")
        ));
        passwordField.sendKeys(TEST_PASSWORD);

        // Ожидаем и проходим капчу
        WebElement captchaIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@data-testid='checkbox-iframe']")));
        driver.switchTo().frame(captchaIframe);

        waitForElementByXpath(driver, "//div[@class='CheckboxCaptcha-Checkbox' and @data-checked='true']");

        driver.switchTo().defaultContent();

        // Нажимаем "Войти" в модальном окне
        WebElement modalLoginSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='submit-button' and @type='submit' and contains(text(), 'Войти')]")
        ));
        modalLoginSubmitButton.click();

        // Проверяем успешность входа
        boolean isLoggedIn = false;
        try {
            WebElement loginSuccessIndicator = waitForElementByXpath(driver, LOGIN_SUCCESS_INDICATOR_XPATH);
            isLoggedIn = loginSuccessIndicator.isDisplayed();
        } catch (Exception ignored) {
        }

        assertTrue(isLoggedIn, "Вход на сайт не выполнен");
    }

    public static WebElement waitForElementByXpath(WebDriver driver, String xpathExpression) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression)));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
