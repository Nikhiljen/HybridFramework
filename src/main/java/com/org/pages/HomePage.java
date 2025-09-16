package com.org.pages;

import com.org.base.BaseTest;
import com.org.utils.Waits;
import org.apache.hc.core5.http.support.Expectation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage extends BaseTest {

    WebDriver driver;
    public static Waits waits;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Get all locator here

    @FindBy(xpath = "//*[@id='logo']//img")
    WebElement image;

    //Method to call from tests cases
    //used PageFactory method to avaoid staleelement exceoptions

    public int sendlogoImage() {
        waits = new Waits(driver);
        String ImageUrl = waits.waitForVisisblity(image,10).getAttribute("src");
        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL(ImageUrl).openConnection());
            connection.setRequestMethod(("GET"));
            connection.connect();
            int responseCode = connection.getResponseCode();
            return responseCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
