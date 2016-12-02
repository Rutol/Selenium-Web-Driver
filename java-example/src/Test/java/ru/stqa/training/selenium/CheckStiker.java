package ru.stqa.training.selenium;

/**
 * Created by Rutol on 01.12.2016.
 */

import org.junit.*;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class CheckStiker {
    private WebDriver driver;
    private WebDriverWait wait;
    int prodQuantity, stickerQuantity;
    WebElement productUnit;
    List<WebElement> prodList, stickerList;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        //Указан путь к последней версии Chrome в portable варианте
        options.setBinary("D:\\Tools\\GoogleChromePortable64\\GoogleChromePortable.exe");

        driver = new ChromeDriver(options); //инициализация драйвера

        wait = new WebDriverWait(driver,10);
    }

    @Test
    public void myCheckStiker() {
        driver.get("http://localhost/litecart/en/"); //открыть главную страницу магазина
        wait = new WebDriverWait(driver,10);

        prodList = driver.findElements(By.id("li.product"));
        // определение списка товаров на главной странице
        prodQuantity=prodList.size(); // сохраняем количество товаров

        for (int i=0; i<prodQuantity; i++  ) {  //проходим по списку товаров
            prodList = driver.findElements(By.id("li.product"));
            productUnit = prodList.get(i);

            //определение списка стикеров (полосок) у товара
            stickerList = driver.findElements(By.cssSelector("li.product .sticker"));
            //определение количества стикеров у товара
            stickerQuantity = stickerList.size();
            //проверка что у товара ровно один стикер
            Assert.assertTrue(stickerQuantity == 1);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}

