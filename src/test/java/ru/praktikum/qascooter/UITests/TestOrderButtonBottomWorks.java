package ru.praktikum.qascooter.UITests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.junit.After;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.praktikum.qascooter.pageobjects.QAScooterHomePageObjects;
import ru.praktikum.qascooter.pageobjects.QAScooterOrderPagesObjects;


public class TestOrderButtonBottomWorks{
    WebDriver driver;
@Before
public void setUp() {
        //Используем менеджер для подготовки драйверов
        //       WebDriverManager.chromedriver().setup();
        //       driver = new ChromeDriver();
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        }
@Test
public void testOrderButtonBottomWorks(){
        // строка для ставнения в assert
        String assertExpectedText="Для кого самокат";
        // инициализируем драйвер адресом страницы
        driver.get("https://qa-scooter.praktikum-services.ru/");
        // создаем экземпляр главной страницы
        QAScooterHomePageObjects homePageObjects=new QAScooterHomePageObjects(driver);
        // ждем окончания загрузки страницы
        homePageObjects.waitForLoadProfileData();
        // закрываем форму согласия с куки
        homePageObjects.clickCookieAgreeButton();
        // прокручиваем страницу до второй кнопки Заказать
        homePageObjects.scrollPageTillOrderBottomButton();
        // нажимаем кнопку "Заказать" внизу главной страницы
        homePageObjects.clickOrderBottomButton();
        // создаем экземпляр класса объектов страниц заказа
        QAScooterOrderPagesObjects orderPagesObjects=new QAScooterOrderPagesObjects(driver);
        // ждем загрузку страницы
        orderPagesObjects.waitForLoadOrderPageData();
        // проводим проверку: загрузилась ли страница оформления заказа?
        assertEquals("Кнопка 'Заказать' внизу страницы не работает",assertExpectedText,
        orderPagesObjects.getOrderpage1HeaderText());
        }
@After
public void teardown() {
        // Зарываем браузер
        driver.quit();
        }

        }
