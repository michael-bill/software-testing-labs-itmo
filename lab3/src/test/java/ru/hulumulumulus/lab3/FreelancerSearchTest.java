package ru.hulumulumulus.lab3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class FreelancerSearchTest extends FlRuTest {

    @Test
    void testSearchFreelancersBySpecialization() {
        // 1. Открываем главную Fl.ru
        driver.get("https://www.fl.ru/");
        log.info("Главная страница Fl.ru открыта.");

        // Пытаемся закрыть уведомление о cookie, если оно есть
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            String cookieButtonXPath = "//button[normalize-space()='Соглашаюсь с условиями' and contains(@class, 'fl-cookie-button')]";
            WebElement cookieAcceptButton = shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath(cookieButtonXPath)));
            cookieAcceptButton.click();
            log.info("Уведомление о cookie принято.");
        } catch (TimeoutException e) {
            log.info("Уведомление о cookie не найдено или не удалось закрыть (это нормально).");
        }

        // 2. Переходим в раздел поиска фрилансеров
        // (На главной странице ссылка "Поиск специалиста на FL.ru")
        WebElement searchSpecialistLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@class, 'fl-home-page__header-freellink') and contains(@href, '/freelancers/')]")
        ));
        searchSpecialistLink.click();
        log.info("Перешли в раздел поиска фрилансеров (/freelancers/).");

        // Ожидаем загрузку каталога фрилансеров (блок категорий)
        WebElement categoryBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(@class, 'b-cat') and contains(@class, 'bg-platinum')]")
        ));
        log.info("Блок категорий в каталоге фрилансеров загружен.");

        // 3 & 4. Выбираем специализацию "Программирование"
        String specializationToClick = "Программирование";
        // Ищем ссылку на категорию внутри блока
        WebElement programmingCategoryLink = wait.until(ExpectedConditions.elementToBeClickable(
            categoryBlock.findElement(By.xpath(".//a[@data-name='programmirovanie' and normalize-space()='" + specializationToClick + "']"))
        ));
        
        // Дополнительно проверяем видимость перед кликом
        wait.until(ExpectedConditions.visibilityOf(programmingCategoryLink));
        programmingCategoryLink.click();
        log.info("Выбрана категория '{}'.", specializationToClick);

        // Ожидаемый результат: отображается список фрилансеров.
        // Проверяем URL и наличие результатов.
        wait.until(ExpectedConditions.urlContains("/freelancers/programmirovanie/"));
        log.info("URL обновлен: {}.", driver.getCurrentUrl());
        
        // Убеждаемся, что заголовок страницы соответствует (или просто ждем таблицу)
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//table[contains(@class, 'catalog-freelancers')]")
        ));
        List<WebElement> searchResults = driver.findElements(
            By.xpath("//table[contains(@class, 'catalog-freelancers')]//tr[contains(@class, 'cf-line')]")
        );
        assertTrue(searchResults.size() > 0, "Результаты поиска по специализации '" + specializationToClick + "' не найдены.");
        log.info("Найдено {} фрилансеров по специализации: {}.", searchResults.size(), specializationToClick);
    }

    @Test
    void testSearchFreelancersByName() {
        // 1. Открываем главную Fl.ru
        driver.get("https://www.fl.ru/");
        log.info("Главная страница Fl.ru открыта.");

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

        // 2. Переходим в раздел поиска фрилансеров
        WebElement searchSpecialistLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@class, 'fl-home-page__header-freellink') and contains(@href, '/freelancers/')]")
        ));
        searchSpecialistLink.click();
        log.info("Перешли в раздел поиска фрилансеров (/freelancers/).");

        // Ожидаем загрузку каталога и доступность поля поиска
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@id='search-request']")
        ));
        log.info("Страница каталога фрилансеров загружена, поле поиска доступно.");

        // 3. Вводим имя "Иван" в поиск
        String searchName = "Иван";
        searchInput.clear();
        searchInput.sendKeys(searchName);
        log.info("В поле поиска введено имя: {}", searchName);

        // 4. Нажимаем "Найти исполнителя"
        WebElement findButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[@data-id='tpl-form-search-exp' and normalize-space()='Найти исполнителя']")
        ));
        findButton.click();
        log.info("Нажата кнопка 'Найти исполнителя'.");

        // Ожидаемый результат: отображается список фрилансеров.
        // Проверяем, что в поле поиска осталось "Иван"
        WebElement searchInputAfterSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(
             By.xpath("//input[@id='search-request']")
        ));
        assertTrue(searchInputAfterSearch.getAttribute("value").contains(searchName),
            "Поле поиска не содержит '" + searchName + "' после поиска.");
        log.info("Поле поиска на странице результатов содержит '{}'.", searchName);

        // Проверяем наличие результатов
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//table[contains(@class, 'catalog-freelancers')]")
        ));
        List<WebElement> searchResults = driver.findElements(
            By.xpath("//table[contains(@class, 'catalog-freelancers')]//tr[contains(@class, 'cf-line')]")
        );
        assertTrue(searchResults.size() > 0, "Результаты поиска по имени '" + searchName + "' не найдены.");
        log.info("Найдено {} фрилансеров по имени: {}.", searchResults.size(), searchName);
    }
}
