package ru.hulumulumulus.lab3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class ProjectSearchTest extends FlRuTest {

    @Test
    void testSearchProjectsByKeyword() {
        // 1. Авторизуемся
        login();
        log.info("Пользователь авторизован.");

        // Переходим на страницу поиска проектов
        driver.get("https://www.fl.ru/search/?type=projects");
        log.info("Открыта страница поиска проектов.");

        // 2. Находим поле поиска
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@id='search-request']")
        ));
        log.info("Поле поиска найдено.");

        // 3. Вводим ключевое слово "сайт"
        String keyword = "сайт";
        searchInput.sendKeys(keyword);
        log.info("Введено ключевое слово: '{}'", keyword);

        // 4. Нажимаем кнопку поиска
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@id='search-button']")
        ));
        searchButton.click();
        log.info("Нажата кнопка поиска.");

        // 5. Проверяем отображение списка проектов
        // Ожидаем хотя бы один результат
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
            By.xpath("//div[@id='search-lenta']/div[contains(@class, 'search-lenta-item')]"), 0
        ));

        List<WebElement> searchResults = driver.findElements(By.xpath("//div[@id='search-lenta']/div[contains(@class, 'search-lenta-item')]"));
        assertTrue(searchResults.size() > 0, "Результаты поиска по ключевому слову не найдены.");
        log.info("Найдено {} проектов по ключевому слову '{}'.", searchResults.size(), keyword);

        // --- Начало Прецедента 4: Подача заявки ---
        // Выбираем первый найденный проект
        WebElement firstProjectLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@id='search-lenta']/div[contains(@class, 'search-lenta-item')][1]//h3/a")
        ));
        log.info("Выбираем первый проект: '{}'", firstProjectLink.getText());
        firstProjectLink.click(); // Переходим на страницу проекта

        boolean actionOnProjectPageConfirmed = false;
        try {
            // Попытка 1: "Откликнуться на проект"
            log.info("Пытаемся откликнуться на проект...");
            WebElement applyButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[@name='new_offer' and contains(normalize-space(), 'Откликнуться')]")
            ));
            applyButton.click();
            log.info("Нажата кнопка 'Откликнуться'.");

            // Проверяем появление всплывающего окна о покупке отклика
            WebElement buyOfferPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("project_answer_popup")
            ));
            assertTrue(buyOfferPopup.isDisplayed(), "Всплывающее окно покупки отклика не появилось.");
            log.info("Всплывающее окно покупки отклика отобразилось.");

            WebElement popupTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@id='project_answer_popup']//div[contains(text(), 'Вы можете купить 1 отклик')]")
            ));
            assertTrue(popupTextElement.isDisplayed(), "Всплывающее окно покупки отклика не содержит ожидаемый текст.");
            log.info("Текст в окне покупки отклика соответствует ожидаемому.");
            
            actionOnProjectPageConfirmed = true;
            log.info("Сценарий 'Откликнуться' успешно выполнен, окно покупки проверено.");

        } catch (Exception e) {
            log.warn("Сценарий 'Откликнуться' не выполнен (кнопка/окно не найдены). Проверяем наличие кнопки 'Разместить заказ'. Ошибка: {}", e.getMessage());
            // Попытка 2: Проверить наличие кнопки "Разместить заказ" (альтернативный сценарий, если пользователь - заказчик)
            try {
                log.info("Пытаемся найти кнопку 'Разместить заказ'...");
                WebElement placeOrderButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(@class, 'ui-button') and normalize-space(.)='Разместить заказ']")
                ));
                actionOnProjectPageConfirmed = true;
                log.info("Найдена кнопка 'Разместить заказ'. Альтернативный сценарий подтвержден.");
            } catch (Exception e2) {
                log.error("Кнопка 'Разместить заказ' также не найдена. Ошибка: {}", e2.getMessage());
            }
        }

        assertTrue(actionOnProjectPageConfirmed, "На странице проекта не удалось ни откликнуться (с проверкой окна), ни найти кнопку 'Разместить заказ'.");
    }
}
