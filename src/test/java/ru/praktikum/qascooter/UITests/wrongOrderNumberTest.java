package ru.praktikum.qascooter.UITests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.praktikum.qascooter.pageobjects.QAScooterHomePageObjects;
import static org.junit.Assert.assertEquals;

public class wrongOrderNumberTest {
    WebDriver driver;

    @Before
    public void setUp() {
        //Используем менеджер для подготовки драйверов
               WebDriverManager.chromedriver().setup();
               driver = new ChromeDriver();
//        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver();
    }
    // Тест проверяет работу приложения при вводе неверного номера заказа в поле Проверки статуса заказа
    @Test
    public void testWrongOrderNumber(){
        String  wrongOrderNumber = "123";
        boolean isNoSuchOrderPictureOnScreen;

        // инициализируем драйвер адресом страницы
        driver.get("https://qa-scooter.praktikum-services.ru/");
        // создаем экземпляр главной страницы
        QAScooterHomePageObjects homePageObjects=new QAScooterHomePageObjects(driver);
        // ждем окончания загрузки страницы
        homePageObjects.waitForLoadProfileData();
        // закрываем форму согласия с куки
        homePageObjects.clickCookieAgreeButton();
        // кликаем кнопку "Статус заказа"
        homePageObjects.clickOrderStateButton();
        driver.manage().window().maximize();
        // вводим неверный номер заказа и нажимаем кнопку Go
        homePageObjects.enterWrongOrderNumber(wrongOrderNumber);
        // Проверяем, появилась ли картинка с надписью "Такого заказа нет" на экране
        isNoSuchOrderPictureOnScreen = homePageObjects.isNoSuchOrderPictureOnScreen();
        assertEquals("Сообщение об отсутствии заказа не появилось!", true, isNoSuchOrderPictureOnScreen);

    }
    @After
    public void teardown() {
        // Зарываем браузер
        driver.quit();
    }

}
