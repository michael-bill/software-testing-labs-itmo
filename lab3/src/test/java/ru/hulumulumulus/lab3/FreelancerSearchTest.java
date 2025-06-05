package ru.hulumulumulus.lab3;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FreelancerSearchTest extends FlRuTest {

    @Test
    void testSearchFreelancersBySpecialization() {
        // Шаг 1: Посетитель заходит на сайт Fl.ru.
        driver.get("https://www.fl.ru/");
        System.out.println("ИНФО: Главная страница Fl.ru открыта.");

        // Попытка закрыть cookie-уведомление, если оно есть
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            String cookieButtonXPath = "//button[normalize-space()='Соглашаюсь с условиями' and contains(@class, 'fl-cookie-button')]";
            WebElement cookieAcceptButton = shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath(cookieButtonXPath)));
            cookieAcceptButton.click();
            System.out.println("ИНФО: Cookie-уведомление 'Соглашаюсь с условиями' было найдено и закрыто.");
        } catch (TimeoutException e) {
            System.out.println("ИНФО: Cookie-уведомление 'Соглашаюсь с условиями' не найдено или не удалось закрыть (это может быть нормально).");
        }

        // Шаг 2: Заказчик переходит в раздел поиска фрилансеров.
        // На главной странице есть заголовок-ссылка "Поиск специалиста на FL.ru"
        WebElement searchSpecialistLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@class, 'fl-home-page__header-freellink') and contains(@href, '/freelancers/')]")
        ));
        searchSpecialistLink.click();
        System.out.println("ИНФО: Переход в раздел поиска фрилансеров (/freelancers/).");

        // Ожидание загрузки страницы каталога фрилансеров.
        // Ждем появления основного блока категорий.
        WebElement categoryBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(@class, 'b-cat') and contains(@class, 'bg-platinum')]") 
        ));
        System.out.println("ИНФО: Блок категорий на странице каталога фрилансеров загружен.");

        // Шаг 3 & 4: Заказчик выбирает специализацию "Программирование" из списка категорий.
        String specializationToClick = "Программирование";
        // Ищем ссылку внутри найденного блока категорий
        WebElement programmingCategoryLink = wait.until(ExpectedConditions.elementToBeClickable(
            categoryBlock.findElement(By.xpath(".//a[@data-name='programmirovanie' and normalize-space()='" + specializationToClick + "']"))
        ));
        
        // Дополнительная проверка на видимость перед кликом
        wait.until(ExpectedConditions.visibilityOf(programmingCategoryLink));
        programmingCategoryLink.click();
        System.out.println("ИНФО: Кликнута категория '" + specializationToClick + "'.");

        // Ожидаемый результат: Отображается список фрилансеров.
        // После клика на категорию, страница должна обновиться.
        // Проверяем, что URL теперь содержит /programmirovanie/ и что есть результаты.
        wait.until(ExpectedConditions.urlContains("/freelancers/programmirovanie/"));
        System.out.println("ИНФО: URL обновлен и содержит /freelancers/programmirovanie/.");
        
        // Убедимся, что заголовок страницы соответствует выбранной категории (или хотя бы обновился)
        // Пример: ожидаем, что H1 будет содержать "Программирование" или "Разработка ПО"
        // Для большей надежности можно просто подождать появления таблицы с результатами.
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//table[contains(@class, 'catalog-freelancers')]")
        ));
        List<WebElement> searchResults = driver.findElements(
            By.xpath("//table[contains(@class, 'catalog-freelancers')]//tr[contains(@class, 'cf-line')]")
        );
        assertTrue(searchResults.size() > 0, "Результаты поиска фрилансеров по специализации '" + specializationToClick + "' не найдены.");
        System.out.println("ИНФО: Найдены фрилансеры (" + searchResults.size() + " на первой странице) по специализации: " + specializationToClick);
    }
}
