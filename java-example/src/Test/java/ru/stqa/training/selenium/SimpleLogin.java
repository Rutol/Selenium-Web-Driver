package ru.stqa.training.selenium;

/**
 * Created by Rutol on 20.11.2016.
 */

import org.junit.*;
import org.openqa.selenium.By;

import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class SimpleLogin {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        //ChromeOptions options = new ChromeOptions();
        //Указан путь к последней версии Chrome в portable варианте
        //options.setBinary("D:\\Tools\\GoogleChromePortable64\\GoogleChromePortable.exe");

        //driver = new ChromeDriver(options); //инициализация драйвера

        DesiredCapabilities caps = new DesiredCapabilities();  //для IE и FF

        //driver = new InternetExplorerDriver(caps); для IE

        //caps.setCapability(FirefoxDriver.MARIONETTE, false);
        //driver = new FirefoxDriver(new FirefoxBinary(caps); // для FF по старой схеме
        driver = new FirefoxDriver(
                new FirefoxBinary(new File("D:\\Tools\\Nightly\\firefox.exe")),
                new FirefoxProfile(), caps);
        System.out.println(((HasCapabilities) driver).getCapabilities()); //для IE
        wait = new WebDriverWait(driver,10);
    }

    @Test
    public void myFirstTest() {
        driver.get("http://localhost/litecart/admin/"); //открыть страницу
        driver.findElement(By.name("username")).sendKeys("admin");
        //найти поле для ввода логина и ввести "admin"
        driver.findElement(By.name("password")).sendKeys("admin");
        //найти поле для ввода пароля и ввести "admin"
        driver.findElement(By.name("login")).click();
        //найти кнопку логина и нажать на нее
        wait.until(titleIs("My Store"));
        //подождать пока не загрузится страница с заголовком "My Store"
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
