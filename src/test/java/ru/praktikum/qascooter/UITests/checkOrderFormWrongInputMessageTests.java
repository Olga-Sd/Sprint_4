package ru.praktikum.qascooter.UITests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.praktikum.qascooter.pageobjects.QAScooterHomePageObjects;
import ru.praktikum.qascooter.pageobjects.QAScooterOrderPagesObjects;

import static org.junit.Assert.assertEquals;
@RunWith(Parameterized.class)
public class checkOrderFormWrongInputMessageTests {
    WebDriver driver;
    private final int fieldNumber;
    private final String wrongInputText;
    private final String wrongInputMessage;

    public checkOrderFormWrongInputMessageTests(int fieldNumber, String wrongInputText, String wrongInputMessage ){
        this.fieldNumber = fieldNumber;
        this.wrongInputText = wrongInputText;
        this.wrongInputMessage = wrongInputMessage;
    }
    @Parameterized.Parameters
    public static Object[][] orderFormWRongInputTestData() {
        return new Object[][] {
                { 0, "Emma", "Введите корректное имя"},
                { 1, "Bovary", "Введите корректную фамилию"},
                { 2, "Yonville","Введите корректный адрес"},
                { 3, "000999","Введите корректный номер"},
                { 4,"","Выберите станцию" },

        };
    }
    @Before
    public void setUp() {
        //Используем менеджер для подготовки драйверов
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
//        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver();
    }

// Тест проверяет факт появления сообщения об ошибке при вводе неверных данных для полей "Имя", "Фамилия",
// "Адрес", "Комментарий для курьера". Для поля "Метро" проверяем сообщение об ошибке в сучае, если поле осталось незаполненным.
// Так как у нас нет требований, то проверки пишу по поведению приложения. На 2 странице оформления заказа сообщения об ошибке
// не появляются ни при каких условиях. В связи с этим для этих полей проверки не пишу.
    @Test
    public void checkWrongInputMessageNameField(){
        boolean isMessageAfterWrongInput;
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
        // заполняем неверными данными поле и кликаем кнопку "Далее"
        isMessageAfterWrongInput = orderPagesObjects.isMessageAfterInputWrongData( fieldNumber, wrongInputText, wrongInputMessage);
        assertEquals("Сообщение о неверном вводе не появилось",true,isMessageAfterWrongInput);

    }

    @After
    public void teardown() {
        // Зарываем браузер
        driver.quit();
    }
}
