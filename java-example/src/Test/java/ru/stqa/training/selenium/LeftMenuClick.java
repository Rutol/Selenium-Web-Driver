package ru.stqa.training.selenium;

/**
 * Created by Rutol on 27.11.2016.
 */

import org.junit.*;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LeftMenuClick {

    private WebDriver driver;
    private WebDriverWait wait;
    int menuQuantity, submenuQuantity;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        //Указан путь к последней версии Chrome в portable варианте
        options.setBinary("D:\\Tools\\GoogleChromePortable64\\GoogleChromePortable.exe");

        driver = new ChromeDriver(options); //инициализация драйвера

        wait = new WebDriverWait(driver,10);
    }

    @Test
    public void myLeftMenuClick() {
        driver.get("http://localhost/litecart/admin/"); //открыть страницу
        driver.findElement(By.name("username")).sendKeys("admin");
        //найти поле для ввода логина и ввести "admin"
        driver.findElement(By.name("password")).sendKeys("admin");
        //найти поле для ввода пароля и ввести "admin"
        driver.findElement(By.name("login")).click();
        //найти кнопку логина и нажать на нее
        wait.until(titleIs("My Store"));
        //подождать пока не загрузится страница с заголовком "My Store"


        List<WebElement> menuPoints = driver.findElements(By.id("app-"));
        List<WebElement> submenuPoints;
        WebElement menuPoint, submenuPoint;
        // определение списка пунктов меню
        menuQuantity=menuPoints.size(); // сохраняем количество пунктов меню

        for (int i=0; i<menuQuantity; i++  ) {  //проходим по пунктам меню
            menuPoints = driver.findElements(By.id("app-"));
            menuPoint= menuPoints.get(i); //выбираем пункт меню
            wait = new WebDriverWait(driver,10);
            menuPoint.click();  // кликаем по меню

            menuPoints = driver.findElements(By.id("app-")); //обновляем список
            menuPoint= menuPoints.get(i); //выбираем пункт меню
            // определение списка пунктов подменю
            submenuPoints = menuPoint.findElements(By.cssSelector("[id^=doc-]"));
            submenuQuantity=submenuPoints.size(); // сохраняем количество пунктов подменю

            if(submenuQuantity>0) { //подменю есть
                for (int j=0;j<submenuQuantity;j++) {
                    menuPoints = driver.findElements(By.id("app-"));  //обновляем список
                    menuPoint= menuPoints.get(i); //выбираем пункт меню
                    // определение списка пунктов подменю
                    submenuPoints = menuPoint.findElements(By.cssSelector("[id^=doc-]"));
                    submenuPoint = submenuPoints.get(j); //выбираем пункт подменю
                    submenuPoint.click();  //кликаем по подменю
                    driver.findElement(By.cssSelector("h1"));  //проверка наличия заголовка
                }
            } else {   // подменю нет
                driver.findElement(By.cssSelector("h1"));  //проверка наличия заголовка
            }
        }

    }



    @After
    public void stop() {
        driver.quit();
        driver = null;
    }


}
