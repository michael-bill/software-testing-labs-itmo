package ru.hulumulumulus.lab3;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectSearchTest extends FlRuTest {

    @Test
    void testSearchProjectsByKeyword() {
        // Шаг 1: Фрилансер авторизуется в системе.
        login();

        // Переходим на страницу поиска проектов
        driver.get("https://www.fl.ru/search/?type=projects");

        // Шаг 2: Фрилансер находит поле для поиска проектов.
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='search-request']")
        ));

        // Шаг 3: Фрилансер вводит ключевое слово "сайт".
        String keyword = "сайт";
        searchInput.sendKeys(keyword);

        // Шаг 4: Фрилансер нажимает кнопку поиска.
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@id='search-button']")
        ));
        searchButton.click();

        // Шаг 5: Проверяем, что список проектов отображается.
        // Ожидаем появления результатов поиска (хотя бы одного элемента)
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
            By.xpath("//div[@id='search-lenta']/div[contains(@class, 'search-lenta-item')]"), 0
        ));

        // Убеждаемся, что найден хотя бы один проект
        List<WebElement> searchResults = driver.findElements(By.xpath("//div[@id='search-lenta']/div[contains(@class, 'search-lenta-item')]"));
        assertTrue(searchResults.size() > 0, "Результаты поиска по ключевому слову не найдены.");

        // --- Начало Прецедента 4: Подача заявки ---
        // Шаг 4.2 (из прецедента): Фрилансер выбирает один из найденных проектов.
        // Кликаем на первый найденный проект
        WebElement firstProjectLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@id='search-lenta']/div[contains(@class, 'search-lenta-item')][1]//h3/a")
        ));
        firstProjectLink.click(); // Переходим на страницу проекта

        boolean действиеНаСтраницеПроектаПодтверждено = false;
        try {
            // Попытка 1: Выполнить сценарий "Откликнуться на проект"
            // Комментарий: Ищем и кликаем кнопку "Откликнуться"
            WebElement applyButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@name='new_offer' and contains(normalize-space(), 'Откликнуться')]")
            ));
            applyButton.click();

            // Комментарий: Проверяем появление всплывающего окна о покупке отклика
            WebElement buyOfferPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("project_answer_popup")
            ));
            assertTrue(buyOfferPopup.isDisplayed(), "Всплывающее окно покупки отклика не появилось (ожидалось после клика на 'Откликнуться').");

            WebElement popupTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@id='project_answer_popup']//div[contains(text(), 'Вы можете купить 1 отклик')]")
            ));
            assertTrue(popupTextElement.isDisplayed(), "Всплывающее окно покупки отклика не содержит ожидаемый текст (ожидалось после клика на 'Откликнуться').");
            
            действиеНаСтраницеПроектаПодтверждено = true;
            System.out.println("ИНФО: Успешно выполнен сценарий 'Откликнуться' и проверено всплывающее окно покупки отклика.");

        } catch (Exception e) { 
            System.out.println("ИНФО: Сценарий 'Откликнуться' не выполнен (кнопка или всплывающее окно не найдены/недоступны). Проверяем наличие кнопки 'Разместить заказ'. Ошибка: " + e.getMessage());
            // Попытка 2: Проверить наличие кнопки "Разместить заказ"
            try {
                // Комментарий: Ищем кнопку "Разместить заказ"
                WebElement placeOrderButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(@class, 'ui-button') and normalize-space(.)='Разместить заказ']")
                ));
                // assertTrue(placeOrderButton.isDisplayed()); // visibilityOfElementLocated уже это проверяет
                действиеНаСтраницеПроектаПодтверждено = true;
                System.out.println("ИНФО: Найдена кнопка 'Разместить заказ'.");
            } catch (Exception e2) {
                System.out.println("ИНФО: Кнопка 'Разместить заказ' также не найдена. Ошибка: " + e2.getMessage());
            }
        }

        assertTrue(действиеНаСтраницеПроектаПодтверждено, "На странице проекта не удалось ни откликнуться (с проверкой всплывающего окна), ни найти кнопку 'Разместить заказ'.");
    }
}
