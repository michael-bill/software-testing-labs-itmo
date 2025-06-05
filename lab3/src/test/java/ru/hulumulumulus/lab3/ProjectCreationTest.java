package ru.hulumulumulus.lab3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait; // Added import
import java.time.Duration;
import java.util.UUID; // Added import for UUID
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class ProjectCreationTest extends FlRuTest {

    @Test
    void testCustomerPlacesNewProject() throws InterruptedException {
        // 1. Авторизуемся
        login();
        log.info("Пользователь авторизован.");

        // 2. Переходим на главную и нажимаем "Разместить заказ"
        driver.get("https://www.fl.ru/");

        // Пытаемся закрыть уведомление о cookie
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            String cookieButtonXPath = "//button[normalize-space()='Соглашаюсь с условиями' and contains(@class, 'fl-cookie-button')]";
            WebElement cookieAcceptButton = shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath(cookieButtonXPath)));
            cookieAcceptButton.click();
            log.info("Уведомление о cookie принято.");
        } catch (TimeoutException e) {
            log.info("Уведомление о cookie не найдено или не удалось закрыть (это нормально).");
        }

        WebElement placeOrderMainPageButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@class, 'ui-button') and normalize-space(.)='Разместить заказ' and contains(@href, '/task/')]")
        ));
        placeOrderMainPageButton.click();
        log.info("Нажата кнопка 'Разместить заказ' на главной.");

        // 3. На странице выбора типа нажимаем "Опубликовать заказ"
        WebElement publishOrderButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@class, 'btn-primary') and normalize-space(.)='Опубликовать заказ' and contains(@href, '/projects/create/')]")
        ));
        publishOrderButton.click();
        log.info("Нажата кнопка 'Опубликовать заказ' (выбор типа публикации).");

        // 4. Обрабатываем возможное модальное окно "Войти как заказчик"
        try {
            WebElement switchToCustomerButton = wait.withTimeout(Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(@class, 'switch-account-button') and normalize-space(.)='Войти как заказчик']")
            ));
            switchToCustomerButton.click();
            log.info("Нажата кнопка 'Войти как заказчик' в модальном окне.");
        } catch (TimeoutException e) {
            log.info("Модальное окно 'Войти как заказчик' не появилось. Продолжаем...");
        }
        
        // Ожидаем загрузку страницы создания проекта
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@id='project-create']")));
        log.info("Страница создания проекта загружена.");

        // 5. Заполняем форму нового проекта
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);
        String projectTitle = "Тестовый проект ТПО " + uniqueSuffix;
        String projectDescription = "Уникальное тестовое описание для ТПО. ID: " + uniqueSuffix + ". Timestamp: " + System.currentTimeMillis();
        String projectBudget = "5000";

        WebElement titleInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ui-input-title']")));
        titleInput.sendKeys(projectTitle);
        log.info("Введен заголовок: {}", projectTitle);

        WebElement descriptionInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea[@id='ui-textarea-description']")));
        descriptionInput.sendKeys(projectDescription);
        log.info("Введено описание проекта.");

        WebElement budgetInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ui-input-budget_value']")));
        budgetInput.sendKeys(projectBudget);
        log.info("Введен бюджет: {}", projectBudget);

        // Выбираем категорию "Программирование"
        WebElement categoryDropdownToggle = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[normalize-space(text())='Категория']/following-sibling::div[1]//div[contains(@class,'vs__dropdown-toggle')]")
        ));
        categoryDropdownToggle.click();
        WebElement categoryOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//ul[@id='vs1__listbox']//li[normalize-space()='Программирование']")
        ));
        categoryOption.click();
        log.info("Выбрана категория 'Программирование'.");

        // Выбираем подкатегорию "iOS"
        WebElement subCategoryDropdownToggle = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[normalize-space(text())='Категория']/following-sibling::div[2]//div[contains(@class,'vs__dropdown-toggle')]")
        ));
        subCategoryDropdownToggle.click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='vs2__listbox']"))); // Ждем список

        WebElement subCategoryOption = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//ul[@id='vs2__listbox']//li[normalize-space()='iOS']")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subCategoryOption); // Скролл
        wait.until(ExpectedConditions.elementToBeClickable(subCategoryOption)); // Ждем кликабельности
        subCategoryOption.click();
        log.info("Выбрана подкатегория 'iOS'.");


        // 6. Нажимаем "Опубликовать проект" на форме
        WebElement publishProjectFormButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[@id='qa-project-wizard-step-3-button-next' and normalize-space(.)='Опубликовать заказ']")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", publishProjectFormButton);
        wait.until(ExpectedConditions.elementToBeClickable(publishProjectFormButton));
        publishProjectFormButton.click();
        log.info("Нажата кнопка 'Опубликовать заказ' на форме.");

        // 7. На странице доп. услуг (VAS) снимаем галочку "Открыть для всех" и продолжаем
        wait.withTimeout(Duration.ofSeconds(25)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Получите больше ответов')]")));
        log.info("Страница доп. услуг (VAS) загружена.");
        
        WebElement forAllCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ui-checkbox-forall']")));
        if (forAllCheckbox.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", forAllCheckbox);
            log.info("Снята галочка 'Открыть заказ для всех'.");
        } else {
            log.info("Галочка 'Открыть заказ для всех' уже была снята.");
        }

        WebElement continueButtonVAS = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(@class, 'ui-button') and normalize-space(.)='Продолжить']")
        ));
        continueButtonVAS.click();
        log.info("Нажата кнопка 'Продолжить' на странице VAS.");

        // 8. Проверяем, что заказ создан (ищем заголовок на странице управления)
        String projectTitleXPath = String.format("//div[@class='text-3' and contains(text(), '%s')]", projectTitle);
        WebElement projectTitleOnPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(projectTitleXPath)));
        assertTrue(projectTitleOnPage.isDisplayed(), "Заголовок созданного проекта ('" + projectTitle + "') не найден.");
        log.info("Проект '{}' успешно создан и отображается.", projectTitle);
    }
}
