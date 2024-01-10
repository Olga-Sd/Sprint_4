// класс объектов и методов главной страницы приложения
package ru.praktikum.qascooter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class QAScooterHomePageObjects {

    WebDriver driver;

    // конструктор экземпляра страницы
    public QAScooterHomePageObjects(WebDriver driver) {
        this.driver = driver;
    }

// локаторы хедера
    // локатор логотипа "Яндекс" в хедере
    private final By logoYandex = By.className("Header_LogoYandex__3TSOI");

// локаторы страницы
    // заголовок "Самокат на пару дней"
    private final By homePageHeaderLabel = By.className("Home_Header__iJKdX");
    // кнопка согласия на куки
    private final By cookieAgreeButton = By.className("App_CookieButton__3cvqF");
    // кнопка "Статус заказа" в правом верхнем углу страницы
    private final By orderStateButton = By.className("Header_Link__1TAG7");
    //кнопка "Заказать" вверху страницы
    private final By orderTopButton = By.className("Button_Button__ra12g");
    //кнопка "Заказать" внизу страницы
    private final By orderBottomButton = By.className("Home_FinishButton__1_cWm");
    //Выпадающий список в разделе «Вопросы о важном»
    private final By accordionListFAQHomepage = By.className("Home_FAQ__3uVm4");
    // поле для ввода номера заказа
    private final By orderNumberFieldPlaceholder = By.xpath(".//*[@placeholder = 'Введите номер заказа']");
    // кнопка "Go!" для поиска статуса заказа, когда номер заказа уже введен
    private final By goButtonForOrderSearch = By.xpath(".//button[text()='Go!']");
    // кнопка "Посмотреть" в разделе информации о статусе заказа. Использую для ожидания загрузки страницы
    private final By lookButton = By.xpath(".//button[text()='Посмотреть']");
    // картинка с сообщением "Такого заказа нет"
    private final By noSuchOrderPicture = By.className("Track_NotFound__6oaoY");
// методы страницы
    //ожидание загрузки страницы
    public void waitForLoadProfileData() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
                driver.findElement(orderTopButton).getText() != null);
    }
    //нажатие кнопки согласия с куки
    public void clickCookieAgreeButton() {
        driver.findElement(cookieAgreeButton).click();
    }

    //прокрутка страницы до выпадающего списка "Вопросы о важном"
    public void scrollPageTillAccordionList() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(accordionListFAQHomepage));
    }
    // прокрутка страницы до кнопки "Заказать" внизу
    public void scrollPageTillOrderBottomButton() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(orderBottomButton));
    }
    // Получение текста выпадающего списка по номеру в списке
    public String accordionItemGetText(int listItemNumber){
        driver.findElement(By.id("accordion__heading-"+listItemNumber)).click();
        return driver.findElement(By.xpath(".//*[@id='accordion__panel-"+listItemNumber+"']/p")).getText();

    }

    // клик по кнопке Заказать вверху страницы
    public void clickOrderTopButton() {
        driver.findElement(orderTopButton).click();
    }

    // клик по кнопке Заказать внизу страницы (после прокрутки)
    public void clickOrderBottomButton() {
        driver.findElement(orderBottomButton).click();
    }
    // Получение текста заголовка страницы
    public String homePageHeaderLabelGetText(){
        return driver.findElement(homePageHeaderLabel).getText();


    }
    // клик по логотипу Яндекса
    public void clickLogoYandex() {
        driver.findElement(logoYandex).click();
    }

    // клик по кнопке "Статус заказа" в правом верхнем цглу страницы
    public void clickOrderStateButton() {
        driver.findElement(orderStateButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(driver ->
                driver.findElement(orderNumberFieldPlaceholder).getText() != null);
    }
    // ввод неверного номера и клик по кнопке подтверждения
    public void enterWrongOrderNumber(String wrongOrderNumber) {
        driver.findElement(orderNumberFieldPlaceholder).click();
        driver.findElement(orderNumberFieldPlaceholder).sendKeys(wrongOrderNumber);
        driver.findElement(goButtonForOrderSearch).click();
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(driver ->
                driver.findElement(lookButton).getText() != null);
    }
    // Проверяем, отобразилась ли картинка с надписью "Такого заказа нет"
    public boolean isNoSuchOrderPictureOnScreen(){
        return driver.findElement(noSuchOrderPicture).isDisplayed();

    }
}
