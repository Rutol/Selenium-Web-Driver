package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;


/**
 * Created by Rutol on 10.12.2016.
 */
public class CheckRegistration {

    private WebDriver driver;
    private WebDriverWait wait;
    String FirstName, LastName, eMailName, testString;



    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        //Указан путь к последней версии Chrome в portable варианте
        options.setBinary("D:\\Tools\\GoogleChromePortable64\\GoogleChromePortable.exe");

        driver = new ChromeDriver(options); //инициализация драйвера

        wait = new WebDriverWait(driver,10);
    }

    @Test
    public void myCheckRegistration() {
        driver.get("http://localhost/litecart/en/"); //открыть главную страницу магазина
        wait = new WebDriverWait(driver,10);

        // находим ссылку регистрации пользователя и щелкаем по ней
        driver.findElement(By.cssSelector("[href*=create_account]")).click();
        wait = new WebDriverWait(driver,10);

        // читаем текущее время - добавляем его к фамилии и имеем уникальный e-mail и пароль каждый раз
        Calendar calendar = Calendar.getInstance();
        int h=calendar.get(calendar.HOUR_OF_DAY);
        int m=calendar.get(calendar.MINUTE);
        int s=calendar.get(calendar.SECOND);

        FirstName="Ivan";
        LastName="Ivanov";
        eMailName=FirstName + Integer.toString(h) + Integer.toString(m) + Integer.toString(s);

        // заполняем форму - только обязательные поля
        driver.findElement(By.name("firstname")).sendKeys(FirstName);
        driver.findElement(By.name("lastname")).sendKeys(LastName);
        driver.findElement(By.name("address1")).sendKeys("3 Buiders st. 13");
        driver.findElement(By.name("postcode")).sendKeys("666666");
        driver.findElement(By.name("city")).sendKeys("Moscow");
        driver.findElement(By.name("email")).sendKeys(eMailName+"@mail.com");
        driver.findElement(By.name("phone")).sendKeys("223-322");
        driver.findElement(By.name("password")).sendKeys(eMailName);
        driver.findElement(By.name("confirmed_password")).sendKeys(eMailName);

        // нажимаем на кнопку Create Account
        driver.findElement(By.name("create_account")).click();

        wait = new WebDriverWait(driver,10);

        // Проверяем что мы залогинились - на странице должен быть соответствующий раздел
        // который имеет заголовок Account
        testString = driver.findElement(By.cssSelector("[id=box-account] .title")).getText();

        Assert.assertTrue(testString.compareTo("Account")==0);

        // Отлогиниваемся
        driver.findElement(By.cssSelector("[href*=logout]")).click();

        wait = new WebDriverWait(driver,10);

        // Проверяем что мы отлогинились - на странице должен быть соответствующий раздел
        // который имеет заголовок Login
        testString = driver.findElement(By.cssSelector("[id=box-account-login] .title")).getText();

        Assert.assertTrue(testString.compareTo("Login")==0);

         // Логинимся под созданным пользователем
        driver.findElement(By.name("email")).sendKeys(eMailName+"@mail.com");
        driver.findElement(By.name("password")).sendKeys(eMailName);
        driver.findElement(By.name("login")).click();

        wait = new WebDriverWait(driver,10);

        // Проверяем что мы залогинились - на странице должен быть соответствующий раздел
        // который имеет заголовок Account
        testString = driver.findElement(By.cssSelector("[id=box-account] .title")).getText();
        Assert.assertTrue(testString.compareTo("Account")==0);

        // Отлогиниваемся
        driver.findElement(By.cssSelector("[href*=logout]")).click();

        // Проверяем что мы отлогинились - на странице должен быть соответствующий раздел
        // который имеет заголовок Login
        testString = driver.findElement(By.cssSelector("[id=box-account-login] .title")).getText();
        Assert.assertTrue(testString.compareTo("Login")==0);

    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
