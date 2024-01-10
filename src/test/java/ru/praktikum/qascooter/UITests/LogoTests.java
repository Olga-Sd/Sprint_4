package ru.praktikum.qascooter.UITests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.junit.After;
import static org.hamcrest.CoreMatchers.containsString;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.Alert;

import org.openqa.selenium.support.ui.WebDriverWait;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.praktikum.qascooter.pageobjects.QAScooterHomePageObjects;
import ru.praktikum.qascooter.pageobjects.QAScooterOrderPagesObjects;
import ru.praktikum.qascooter.pageobjects.yandexDzenPageObjects;
import java.time.Duration;

public class LogoTests {
    WebDriver driver;

    @Before
    public void setUp() {
        //Используем менеджер для подготовки драйверов
               WebDriverManager.chromedriver().setup();
               driver = new ChromeDriver();
//       WebDriverManager.firefoxdriver().setup();
//       driver = new FirefoxDriver();
    }
    // Тест проверяет загрузку главной страницы приложения "ЯндексСамокат" после клика на логотип "Самокат"
    // в левом верхнем углу страницы приложения
    @Test
    public void testScooterLogo(){
        // строка для ставнения в assert
        String assertExpectedText="Самокат\n" +
                "на пару дней";
        // инициализируем драйвер адресом страницы
        driver.get("https://qa-scooter.praktikum-services.ru/");
        // создаем экземпляр главной страницы
        QAScooterHomePageObjects homePageObjects=new QAScooterHomePageObjects(driver);
        // ждем окончания загрузки страницы
        homePageObjects.waitForLoadProfileData();
        // закрываем форму согласия с куки
        homePageObjects.clickCookieAgreeButton();
        // нажимаем кнопку "Заказать" в правом верхнем углу главной страницы
        homePageObjects.clickOrderTopButton();
        // создаем экземпляр класса объектов страниц заказа
        QAScooterOrderPagesObjects orderPagesObjects = new QAScooterOrderPagesObjects(driver);
        // ждем загрузку страницы
        orderPagesObjects.waitForLoadOrderPageData();
        // кликаем логотип "Самокат" в левом верхнем углу страницы
        orderPagesObjects.clickScooterLogo();
        // ждем окончания загрузки страницы
        homePageObjects.waitForLoadProfileData();
        // проводим проверку: с помощью заголовка "Самокат на пару дней" определяем, загрузилась ли нужная страница

        MatcherAssert.assertThat(homePageObjects.homePageHeaderLabelGetText(), containsString(assertExpectedText));
    }
    // Тест проверяет открытие работы клика по логотипу "Яндекс" в левой верхней части страницы приложения "ЯндексСамокат"
    // В связи с отсутствием требований пишу тест на открытие страницы "Дзена", так, как сейчас работает приложение,
    // хотя по моему пользовательскому опыту, мне кажется, что должна загружаться главная страница Яндекса
    @Test
    public void testYandexLogo() {
        //WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
        // переменные для сравнения в assert
        String assertExpectedText;
        String assertActualText;
        // инициализируем драйвер адресом страницы
        driver.get("https://qa-scooter.praktikum-services.ru/");
        // создаем экземпляр главной страницы
        QAScooterHomePageObjects homePageObjects = new QAScooterHomePageObjects(driver);
        // ждем окончания загрузки страницы
        homePageObjects.waitForLoadProfileData();
        //кликаем по логотипу Яндекса
        homePageObjects.clickLogoYandex();
        //создаем экземпляр страницы Яндекса
        yandexDzenPageObjects yandexDzenObj = new yandexDzenPageObjects(driver);
        yandexDzenObj.waitDzenTabLoads();
        //Thread.sleep(5000);
        //presenceOfElementLocated()
        //переключаемся на последнюю открытую вкладку
        for (String Tab: driver.getWindowHandles()){
            driver.switchTo().window(Tab);

        }
        if( driver instanceof FirefoxDriver) {
            yandexDzenObj.waitDzenPageInFireFoxLoads();
            assertActualText = driver.getCurrentUrl();
            assertExpectedText =  "https://dzen.ru/";}
        else{
            assertActualText = yandexDzenObj.searchFieldPlaceholderGetText();
            assertExpectedText = "Поиск Яндекса";
        }
        Assert.assertThat(assertActualText, containsString(assertExpectedText));
    }
    @After
    public void teardown() {
        // Зарываем браузер
        driver.quit();
    }

}