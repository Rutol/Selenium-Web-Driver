package ru.stqa.training.selenium;

import org.junit.*;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        //Указан путь к последней версии Chrome в portable варианте
        options.setBinary("D:\\Tools\\GoogleChromePortable64\\GoogleChromePortable.exe");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver,10);
    }

    @Test
    public void myFirstTest() {
        driver.get("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.name("btnG")).click();
        wait.until(titleIs("webdriver - Поиск в Google"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
