package ru.praktikum.qascooter.UITests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;

import org.openqa.selenium.firefox.FirefoxDriver;
import ru.praktikum.qascooter.pageobjects.QAScooterHomePageObjects;

@RunWith(Parameterized.class)
public class MainPageAccordionListTest {

    WebDriver driver;
    //переменная для хранения номера пункта в выпадающем списке
    private final int listItemNumber;
    //переменная для хранения текста пункта в выпадающем списке
    private final String listItemText;

    public MainPageAccordionListTest(int listItemNumber, String listItemText){
        //инициализируем данные класса тестовыми данными
        this.listItemNumber = listItemNumber;
        this.listItemText = listItemText;
    }
    // тестовые данные
    @Parameterized.Parameters
    public static Object[][] accordionListTestData() {
        return new Object[][] {
                { 0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                { 1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                { 2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                { 3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                { 4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                { 5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                { 6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                { 7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }
    @Before
    public void setUp() {
        //Используем менеджер для подготовки драйверов
        WebDriverManager.chromedriver().setup();
         driver = new ChromeDriver();
//        WebDriverManager.firefoxdriver().setup();
//         driver = new FirefoxDriver();
    }
    @Test
    public void checkTextAccordionListFAQHomepage(){
        // инициализируем драйвер адресом страницы
        driver.get("https://qa-scooter.praktikum-services.ru/");
        // создаем экземпляр главной страницы
        QAScooterHomePageObjects homePageObjects = new QAScooterHomePageObjects(driver);
        // ждем окончания загрузки страницы
        homePageObjects.waitForLoadProfileData();
        // закрываем форму согласия с куки
        homePageObjects.clickCookieAgreeButton();
        // прокручиваем страницу до тестируемого списка
        homePageObjects.scrollPageTillAccordionList();
        // проводим проверку: сравниваем полученный от элемента такст с тестовыми данными
        assertEquals("Неверный текст сообщения",listItemText,homePageObjects.accordionItemGetText(listItemNumber));

    }
    @After
    public void teardown() {
        // Зарываем браузер
        driver.quit();
    }

}
