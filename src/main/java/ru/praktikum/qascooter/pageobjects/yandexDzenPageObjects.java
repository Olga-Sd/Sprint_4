// класс объектов и методов страницы Дзена
package ru.praktikum.qascooter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

import java.time.Duration;

public class yandexDzenPageObjects {
    WebDriver driver;
    WebDriverWait wait;

    // конструктор экземпляра страницы
    public yandexDzenPageObjects(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    //плейсхолдер поисковой строки Дзена
    private final By searchFieldPlaceholder = By.className("dzen-search-arrow-common__placeholder");
    //октно, предлагающее установить браузер Яндекса
    private final By firefoxYandexSetupSuggestionWindow = By.xpath(".//*[@class = 'u1b89ce91']");
    // берем текст плейсхолдера поисковой строки
    public String searchFieldPlaceholderGetText() {
        return driver.findElement(searchFieldPlaceholder).getText();
    }
    // ожидание загрузки вкладки
    public void waitDzenTabLoads(){
    wait.until(numberOfWindowsToBe(2));
}
    // ожидание загрузки страницы в фаерфоксе
    public void waitDzenPageInFireFoxLoads() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firefoxYandexSetupSuggestionWindow));
    }

}