package ru.praktikum.qascooter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class QAScooterOrderPagesObjects {

    WebDriver driver;

    public QAScooterOrderPagesObjects(WebDriver driver) {
        this.driver = driver;
    }
// локаторы хедера
    // локатор логотипа "Самокат" в хедере
    private final By logoScooter = By.className("Header_LogoScooter__3lsAR");

// локаторы 1 страницы оформления заказа
    // локатор для заголовка страницы
    private final By oderpage1HeaderLabel = By.className("Order_Header__BZXOb");

    // локатор для выбора простых элементов ввода: Имя, Фамилия, Адрес, Телефон
    private final By inputContainers = By.xpath(".//*[@placeholder = '* Имя' or @placeholder = " +
            "'* Фамилия' or @placeholder = '* Адрес: куда привезти заказ'" +
            " or @placeholder = '* Телефон: на него позвонит курьер']");

    //локатор для ввода номера станции в поле Станция метро.
    private final By selectSearchInput = By.className("select-search__input");
    // локатор для кнопки Далее
    private final By orderNextButton = By.xpath(".//button[text()='Далее']");
// локаторы 2 страницы оформления заказа
    // локатор поля выбора даты (когда привезти самокат)
    private final By orderDateField = By.xpath(".//*[@placeholder='* Когда привезти самокат']");
    // локатор поля выбора на сколько дней привезти самокат
    private final By rentalPeriodField = By.className("Dropdown-placeholder");
    // локатор для поля Выбор цвета самоката - перенесен в метод, т к требует тестовые данные для корректной работы
    //private final By scooterColourChoiceField = By.xpath(".//*[text() = 'чёрный жемчуг']");
    // локатор для поля Комментарии для курьера
    private  final By commentForCourierField = By.xpath(".//*[@placeholder = 'Комментарий для курьера']");
    // локатор для кнопки Заказать
    private final By orderButton = By.xpath(".//button[text()='Заказать']");
    // кнопка Нет модальной формы "Хотите оформить заказ?"
    private final By noOrderButton = By.xpath(".//*[text()='Нет']");
    // кнопка Да модальной формы "Хотите оформить заказ?"
    private final By yesOrderButton = By.xpath(".//*[text()='Да']");
    //
    private final By seeOrderStateButton = By.xpath(".//*[text()='Посмотреть статус']");

    private final By orderConfirmFormHeader = By.className("Order_ModalHeader__3FDaJ");

    public void waitForLoadOrderPageData() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
        driver.findElement(oderpage1HeaderLabel).getText() != null);
    }

    public void fillDataInInputFields(String[] Data) {
        int i = 0;
        String[] orderData = Data;
        By scooterColourChoiceField = By.xpath(".//*[text() = '"+orderData[7]+"']");

        //заполняем поле Метро на 1 странице заказа
        driver.findElement(selectSearchInput).click();
        driver.findElement(By.xpath(".//button[@value = '" + orderData[i] + "']")).click();
        i++;
        // заполняем остальные поля ввода 1-ой страницы
        List<WebElement> inputElements = driver.findElements(inputContainers);
        for (WebElement inputElement : inputElements) {
            inputElement.sendKeys(orderData[i]);
            i++;
        }

        //кликаем кнопку Далее и ждем прогрузку страницы
        driver.findElement(orderNextButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(rentalPeriodField));

        // заполняем поле Комментарий для курьера
        driver.findElement(commentForCourierField).sendKeys(orderData[i]);
        i++;
        //
        driver.findElement(By.xpath(".//*[text() = '"+orderData[i]+"']")).click();
        i++;
        driver.findElement(rentalPeriodField).click();

        driver.findElement(scooterColourChoiceField).click();
        i++;
        driver.findElement(orderDateField).sendKeys(orderData[i]);

    }
    // клик на знопку "Заказать" и ожидание загрузки формы "Хотите оформить заказ?"
    public void clickOrderButton() {

        driver.findElements(orderButton).get(1).click();
        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.visibilityOfElementLocated(noOrderButton));
                        //"Order_ModalHeader__3FDaJ")));
    }

    public void clickYesOrderButton(){
        driver.findElement(yesOrderButton).click();
                //By.xpath("//*[contains(@class,'Button_Middle__1CSJM') and contains(text(),'Да')]")).click();
        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.visibilityOfElementLocated(seeOrderStateButton));
    }
    // получаем текст модальной формы "Заказ оформлен". Текс нужен для проверки(assert) флоу оформления заказа
    public String getOrderConfirmFormHeaderText(){
        return  driver.findElement(orderConfirmFormHeader).getText();
    }

    // получаем текст формы "Для кого самокат". Текс нужен для проверки(assert) работы кнопки Заказать внизу страницы
    public String getOrderpage1HeaderText(){
        return  driver.findElement(oderpage1HeaderLabel).getText();
    }
    // кликаем по логотипу "Самокат"
    public void clickScooterLogo(){
        driver.findElement(logoScooter).click();
    }

    public boolean isMessageAfterInputWrongData(int fieldNumber, String wrongInputText, String wrongInputMessage){
        if (fieldNumber != 4) {
            driver.findElements(inputContainers).get(fieldNumber).sendKeys(wrongInputText);

        }
        driver.findElement(orderNextButton).click();
        return driver.findElement(By.xpath(".//div[text()='"+wrongInputMessage+"']")).isDisplayed();
    }

}
