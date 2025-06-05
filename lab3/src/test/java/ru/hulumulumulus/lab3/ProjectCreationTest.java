package ru.hulumulumulus.lab3;

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

public class ProjectCreationTest extends FlRuTest {

    @Test
    void testCustomerPlacesNewProject() throws InterruptedException {
        // Шаг 1: Заказчик авторизуется в системе.
        login(); 
        System.out.println("ИНФО: Пользователь авторизован.");

        // Шаг 2: Заказчик находит и нажимает кнопку "Разместить заказ" на главной странице.
        driver.get("https://www.fl.ru/");

        // Попытка закрыть cookie-уведомление, если оно есть
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            // Точный XPath для кнопки "Соглашаюсь с условиями" на основе предоставленного HTML
            String cookieButtonXPath = "//button[normalize-space()='Соглашаюсь с условиями' and contains(@class, 'fl-cookie-button')]";
            WebElement cookieAcceptButton = shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath(cookieButtonXPath)));
            cookieAcceptButton.click();
            System.out.println("ИНФО: Cookie-уведомление 'Соглашаюсь с условиями' было найдено и закрыто.");
        } catch (TimeoutException e) {
            System.out.println("ИНФО: Cookie-уведомление 'Соглашаюсь с условиями' не найдено или не удалось закрыть в течение 5 сек (это может быть нормально).");
        }

        WebElement placeOrderMainPageButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@class, 'ui-button') and normalize-space(.)='Разместить заказ' and contains(@href, '/task/')]")
        ));
        placeOrderMainPageButton.click();
        System.out.println("ИНФО: Нажата кнопка 'Разместить заказ' на главной странице.");

        // Шаг 3: На странице выбора типа публикации нажимаем "Опубликовать заказ"
        WebElement publishOrderButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@class, 'btn-primary') and normalize-space(.)='Опубликовать заказ' and contains(@href, '/projects/create/')]")
        ));
        publishOrderButton.click();
        System.out.println("ИНФО: Нажата кнопка 'Опубликовать заказ' (выбор типа).");

        // Шаг 4: Обработка модального окна "Войти как заказчик" (если появится)
        try {
            // Используем более короткий таймаут для проверки необязательного элемента
            WebElement switchToCustomerButton = wait.withTimeout(Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(@class, 'switch-account-button') and normalize-space(.)='Войти как заказчик']")
            ));
            // Элемент видим, значит, кликаем
            switchToCustomerButton.click();
            System.out.println("ИНФО: Нажата кнопка 'Войти как заказчик' в модальном окне.");
        } catch (TimeoutException e) {
            System.out.println("ИНФО: Модальное окно 'Войти как заказчик' не появилось или не было найдено в течение 5 секунд. Продолжаем...");
        }
        
        // Ожидание загрузки страницы создания проекта (проверяем по заголовку формы)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@id='project-create']"))); 
        System.out.println("ИНФО: Страница создания проекта загружена.");

        // Шаг 5: Заказчик заполняет форму нового проекта
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8); // 8-char unique ID
        String projectTitle = "Тестовый проект ТПО " + uniqueSuffix;
        String projectDescription = "Уникальное тестовое описание для ТПО. ID: " + uniqueSuffix + ". Timestamp: " + System.currentTimeMillis();
        String projectBudget = "5000";

        WebElement titleInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ui-input-title']")));
        titleInput.sendKeys(projectTitle);
        System.out.println("ИНФО: Введен заголовок: " + projectTitle);

        WebElement descriptionInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea[@id='ui-textarea-description']")));
        descriptionInput.sendKeys(projectDescription);
        System.out.println("ИНФО: Введено описание.");

        WebElement budgetInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ui-input-budget_value']")));
        budgetInput.sendKeys(projectBudget);
        System.out.println("ИНФО: Введен бюджет: " + projectBudget);

        // Выбор категории и подкатегории
        // Первая категория (например, "Программирование")
        WebElement categoryDropdownToggle = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[normalize-space(text())='Категория']/following-sibling::div[1]//div[contains(@class,'vs__dropdown-toggle')]")
        ));
        categoryDropdownToggle.click();
        WebElement categoryOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//ul[@id='vs1__listbox']//li[normalize-space()='Программирование']")
        ));
        categoryOption.click();
        System.out.println("ИНФО: Выбрана категория 'Программирование'.");

        // Пауза, чтобы дать Vue.js обновить список подкатегорий (если необходимо)
        // Thread.sleep(500); // Не лучший вариант, лучше использовать явное ожидание, если возможно

        // Вторая категория (подкатегория, например, "iOS")
        WebElement subCategoryDropdownToggle = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[normalize-space(text())='Категория']/following-sibling::div[2]//div[contains(@class,'vs__dropdown-toggle')]")
        ));
        subCategoryDropdownToggle.click(); // Открываем дропдаун подкатегорий
        
        // Явно ждем, пока список опций подкатегории (ul) станет видимым
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='vs2__listbox']")));

        WebElement subCategoryOption = wait.until(ExpectedConditions.presenceOfElementLocated( // First ensure it's present
            By.xpath("//ul[@id='vs2__listbox']//li[normalize-space()='iOS']")
        ));
        // Scroll the option into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subCategoryOption);
        // Wait for it to be clickable after scrolling
        wait.until(ExpectedConditions.elementToBeClickable(subCategoryOption));
        subCategoryOption.click();
        System.out.println("ИНФО: Выбрана подкатегория 'iOS'.");


        // Шаг 6: Заказчик нажимает кнопку "Опубликовать проект".
        WebElement publishProjectFormButton = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[@id='qa-project-wizard-step-3-button-next' and normalize-space(.)='Опубликовать заказ']")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", publishProjectFormButton);
        // Дополнительное ожидание кликабельности после скролла
        wait.until(ExpectedConditions.elementToBeClickable(publishProjectFormButton)); 
        publishProjectFormButton.click();
        System.out.println("ИНФО: Нажата кнопка 'Опубликовать заказ' на форме.");

        // Шаг 7: На странице VAS (доп. услуг) убираем галочку "Открыть заказ для всех" и нажимаем "Продолжить"
        // Ожидаем загрузку страницы VAS, предполагая, что проект всегда уникален
        wait.withTimeout(Duration.ofSeconds(25)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Получите больше ответов')]")));
        System.out.println("ИНФО: Страница VAS загружена.");
        
        WebElement forAllCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ui-checkbox-forall']")));
        if (forAllCheckbox.isSelected()) {
            // Используем JavascriptExecutor для клика, если стандартный не сработает
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", forAllCheckbox);
            System.out.println("ИНФО: Снята галочка 'Открыть заказ для всех'.");
        } else {
            System.out.println("ИНФО: Галочка 'Открыть заказ для всех' уже была снята.");
        }

        WebElement continueButtonVAS = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(@class, 'ui-button') and normalize-space(.)='Продолжить']")
        ));
        continueButtonVAS.click();
        System.out.println("ИНФО: Нажата кнопка 'Продолжить' на странице VAS.");

        // Шаг 8: Проверяем, что заказ создан (название проекта на странице управления)
        String projectTitleXPath = String.format("//div[@class='text-3' and contains(text(), '%s')]", projectTitle);
        WebElement projectTitleOnPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(projectTitleXPath)));
        assertTrue(projectTitleOnPage.isDisplayed(), "Заголовок созданного проекта ('" + projectTitle + "') не найден на странице управления.");
        System.out.println("ИНФО: Проект '" + projectTitle + "' успешно создан и отображается на странице управления.");
    }
}
