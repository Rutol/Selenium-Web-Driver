package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by Rutol on 11.12.2016.
 */
public class CheckNewProduct {

    private WebDriver driver;
    private WebDriverWait wait;
    String Name, ProdName, validFrom, validTo, prefix;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        //Указан путь к последней версии Chrome в portable варианте
        options.setBinary("D:\\Tools\\GoogleChromePortable64\\GoogleChromePortable.exe");

        driver = new ChromeDriver(options); //инициализация драйвера

        wait = new WebDriverWait(driver,10);
    }


    @Test
    public void myCheckNewProduct() {

        driver.get("http://localhost/litecart/admin/"); //открыть страницу
        driver.findElement(By.name("username")).sendKeys("admin");
        //найти поле для ввода логина и ввести "admin"
        driver.findElement(By.name("password")).sendKeys("admin");
        //найти поле для ввода пароля и ввести "admin"
        driver.findElement(By.name("login")).click();
        //найти кнопку логина и нажать на нее
        wait.until(titleIs("My Store"));
        //подождать пока не загрузится страница с заголовком "My Store"

        // читаем текущее время - добавляем его к фамилии и имеем уникальный e-mail и пароль каждый раз
        Calendar calendar = Calendar.getInstance();
        int h=calendar.get(calendar.HOUR_OF_DAY);
        int m=calendar.get(calendar.MINUTE);
        int s=calendar.get(calendar.SECOND);

        Name="Donald McDown";
        prefix =Integer.toString(h) + Integer.toString(m) + Integer.toString(s);
        ProdName=Name + " " + prefix;


        int y=calendar.get(calendar.YEAR);
        int month=calendar.get(calendar.MONTH);
        int d=calendar.get(calendar.DAY_OF_MONTH);

        validFrom=Integer.toString(y);
        validTo=Integer.toString(y+2);

        if(month<10) {
            validFrom=validFrom+"-0"+Integer.toString(month);
            validTo=validTo+"-0"+Integer.toString(month);
        } else {
            validFrom=validFrom+"-"+Integer.toString(month);
            validTo=validTo+"-"+Integer.toString(month);
        }

        if(d<10) {
            validFrom=validFrom+"-0"+Integer.toString(d);
            validTo=validTo+"-0"+Integer.toString(d);
        } else {
            validFrom=validFrom+"-"+Integer.toString(d);
            validTo=validTo+"-"+Integer.toString(d);
        }


        driver.findElement(By.cssSelector("[href*=catalog]")).click();
        // открыть каталог

        driver.findElement(By.linkText("Add New Product")).click();
        // открываем форму регистрации нового продукта

        wait = new WebDriverWait(driver,10);

        driver.findElement(By.name("status")).click();
        // устанавливаем статус Enabled
        driver.findElement(By.name("name[en]")).clear();
        driver.findElement(By.name("name[en]")).sendKeys(ProdName);
        // вводим название товара
        driver.findElement(By.name("code")).sendKeys(prefix+Keys.TAB);
        // вводим код товара
        driver.findElement(By.xpath("(//input[@name='categories[]'])[2]")).click();
        // устанавливаем категорию Rubber Ducks

        driver.findElement(By.xpath("(//input[@name='product_groups[]'])[3]")).click();
        // Устанавливаем группу Unisex

        driver.findElement(By.name("quantity")).sendKeys("1");
        // устанавливаем количество 1
        driver.findElement(By.name("date_valid_from")).sendKeys(validFrom);
        // устанавливаем дату начала годности
        driver.findElement(By.name("date_valid_to")).sendKeys(validTo);
        // устанавливаем дату конца годности

        driver.findElement(By.linkText("Information")).click();
        // переходим на вкладку Information
        wait = new WebDriverWait(driver,10);
        new Select(driver.findElement(By.name("manufacturer_id"))).selectByVisibleText("ACME Corp.");
        // выбираем корпорацию
        driver.findElement(By.name("keywords")).sendKeys("Duck");
        // Ввводим ключевое слово
        driver.findElement(By.name("short_description[en]")).sendKeys("Duck");
        // задаем краткое описание
        driver.findElement(By.name("description[en]")).sendKeys(ProdName+" is cool!");
        // задаем описание
        driver.findElement(By.name("head_title[en]")).sendKeys(ProdName);
        // задаем заголовок
        driver.findElement(By.name("meta_description[en]")).sendKeys("666666666");
        // задаем метаописание

        driver.findElement(By.linkText("Data")).click();
        // переходим на вкладку Data
        wait = new WebDriverWait(driver,10);
        driver.findElement(By.name("sku")).sendKeys(prefix);
        // заполняем поле SKU
        driver.findElement(By.name("gtin")).sendKeys(prefix);
        // заполняем поле GTIN
        driver.findElement(By.name("taric")).sendKeys(prefix);
        // заполняем поле TARIC
        driver.findElement(By.name("weight")).sendKeys("1");
        // задаем вес
        driver.findElement(By.name("dim_x")).sendKeys("10");
        driver.findElement(By.name("dim_y")).sendKeys("10");
        driver.findElement(By.name("dim_z")).sendKeys("10");
        // задаем размеры
        driver.findElement(By.name("attributes[en]")).sendKeys("None");
        // задаем атрибуты

        driver.findElement(By.linkText("Prices")).click();
        // переходим на вкладку Prices
        wait = new WebDriverWait(driver,10);
        driver.findElement(By.name("purchase_price")).sendKeys("13");
        // задаем цену
        new Select(driver.findElement(By.name("purchase_price_currency_code"))).selectByVisibleText("Euros");
        // выбираем валюту
        driver.findElement(By.name("gross_prices[USD]")).sendKeys("20");
        // задаем цену в долларах

        driver.findElement(By.name("save")).click();
        // сохраняем продукт
        wait = new WebDriverWait(driver,10);


        // Проверяем наличие такого элемента на странице
        driver.findElement(By.linkText(ProdName));
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
