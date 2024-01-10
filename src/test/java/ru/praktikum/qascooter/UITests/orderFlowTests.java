package ru.praktikum.qascooter.UITests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.containsString;
import org.hamcrest.MatcherAssert;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;

import org.openqa.selenium.firefox.FirefoxDriver;
import ru.praktikum.qascooter.pageobjects.QAScooterHomePageObjects;
import ru.praktikum.qascooter.pageobjects.QAScooterOrderPagesObjects;

@RunWith(Parameterized.class)
public class orderFlowTests {
    WebDriver driver;
    String[] orderData;

    public orderFlowTests(String metro, String name, String surname, String adress, String phone, String comment,
                          String colour, String leaseDuration, String orderDate){
    this.orderData = new String[]{metro, name, surname, adress, phone, comment, colour, leaseDuration, orderDate};
    }

    @Parameterized.Parameters
    public static Object[][] orderData() {
        /* Тестовые данные представлены в виде массива сос ледующими позициями:
        0: номер станции метро (28 - Динамо, 48 - Мякинино)
        1,2: Имя, фамилия
        3: адрес
        4: телефон
        5: комментарий курьеру
        6: цвет самоката
        7: срок аренды
        8: дата доставки
         */
        return new Object[][]{
                {"28","Иван", "Иванов", "Ломоносовский проспект 3а", "+1234567890",
                        "Домофон не работает","чёрный жемчуг","двое суток","12.02.2024"},
                {"48","Петр", "Смирнов", "Хороший адрес", "12345678900",
                        "Домофон уже работает","серая безысходность","сутки","5.09.2023"}
        };
    }
    // перед каждым тестом создаем драйвер
    @Before
    public void setUp() {
        //Используем менеджер для подготовки драйверов
 //       WebDriverManager.chromedriver().setup();
 //       driver = new ChromeDriver();
        WebDriverManager.firefoxdriver().setup();
         driver = new FirefoxDriver();
    }
    @Test
    public void orderFlowTestOrderButton1(){
        // строка для ставнения в assert
        String assertExpectedText = "Заказ оформлен";
        // инициализируем драйвер адресом страницы
        driver.get("https://qa-scooter.praktikum-services.ru/");
        // создаем экземпляр главной страницы
        QAScooterHomePageObjects homePageObjects = new QAScooterHomePageObjects(driver);
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
        // заполняем поля заказа тестовыми данными
        orderPagesObjects.fillDataInInputFields(orderData);
        // нажимаем кнопку Заказать для завершения оформления заказа
        orderPagesObjects.clickOrderButton();
        // нажимаем кнопку Да формы "Хотите оформить заказ?"
        orderPagesObjects.clickYesOrderButton();
        // проводим проверку: ищем текст, который ожидаем получить в тексте формы подтверждения оформления заказа
        MatcherAssert.assertThat(orderPagesObjects.getOrderConfirmFormHeaderText(), containsString(assertExpectedText));

    }
    @After
    public void teardown() {
        // Зарываем браузер
        driver.quit();
    }

}
