package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by Rutol on 20.12.2016.
 */
public class CheckNewTabs {

    private WebDriver driver;
    private WebDriverWait wait;
    int countryQuantity, linksQuantity; // количество стран в списке
    int randomIndex;
    WebElement countryRow;  // строка по стране
    List<WebElement> countryRows, listLinks;  // список стран, список внешних ссылок
    String originalWindow, newWindow;
    Set<String> existingWindows;

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles=driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size()>0 ? handles.iterator().next():null;
            }
        };
    }

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        //Указан путь к последней версии Chrome в portable варианте
        options.setBinary("D:\\Tools\\GoogleChromePortable64\\GoogleChromePortable.exe");

        driver = new ChromeDriver(options); //инициализация драйвера

        wait = new WebDriverWait(driver,10);
    }


    @Test
    public void myCheckNewTabs() {

        driver.get("http://localhost/litecart/admin/"); //открыть страницу
        driver.findElement(By.name("username")).sendKeys("admin");
        //найти поле для ввода логина и ввести "admin"
        driver.findElement(By.name("password")).sendKeys("admin");
        //найти поле для ввода пароля и ввести "admin"
        driver.findElement(By.name("login")).click();
        //найти кнопку логина и нажать на нее
        wait.until(titleIs("My Store"));
        //подождать пока не загрузится страница с заголовком "My Store"

        //открыть страницу со списком стран
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        wait.until(titleContains("Countries")); // ждем загрузки страницы

        //определение списка строк в таблице стран
        countryRows = driver.findElements(By.cssSelector("[name=countries_form] .row"));

        // сохраняем количество строк, т.е. стран в списке
        countryQuantity=countryRows.size();

        final Random random = new Random();

        // выбираем номер случайно страны из списка
        randomIndex = random.nextInt(countryQuantity-1);

        countryRow=countryRows.get(randomIndex);  // выбираем случайную страну
        countryRow.findElement(By.cssSelector("a")).click();
        // открываем страницу выбранной страны
        wait.until(titleContains("Edit Country"));  // ждем загрузки страницы

        listLinks = driver.findElements(By.cssSelector("form .fa-external-link"));
        // получаем список внешних ссылок

        linksQuantity = listLinks.size();  // определяем количество ссылок

        for (int i=0; i<linksQuantity; i++) {

            originalWindow=driver.getWindowHandle();
            // сохранили идентификатор текущего окна

            existingWindows=driver.getWindowHandles();
            // сохранили идентификаторы уже открытых окон

            listLinks.get(i).click(); // кликаем по ссылке из найденного списка
            newWindow=wait.until(anyWindowOtherThan(existingWindows));
            // получаем идентификатор нового окна

            driver.switchTo().window(newWindow);  // переключаемся в новое окно

            driver.close();  // закрываем окно

            driver.switchTo().window(originalWindow); // вернулись в исходное окно
        }
    }

    @After
    public void stop() {
        driver.get("http://localhost/litecart/admin/logout.php");
        driver.quit();
        driver = null;
    }


}
