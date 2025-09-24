package com.org.pages;

import com.org.base.Base;
import com.org.utils.LoggerHelper;
import com.org.utils.Waits;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage extends Base {

    WebDriver driver;
    public static Waits waits;
    private static final Logger logger = LoggerHelper.getLogger(HomePage.class);


//   used PageFactory method to avoid stale element exceptions
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Get all locator here

    @FindBy(xpath = "//*[@class='header-logo']//img")
    private WebElement image;

    @FindBy(xpath= "//*[@class='header-links']//a")
    private List<WebElement> headerList;

    @FindBy(xpath = "//*[@id='small-searchterms']")
    private WebElement searchBox;

    @FindBy(xpath = "//*[contains(@class,'search-box-button')]")
    private WebElement searchButton;

    //Method to call from tests cases
    public int ImageProcessor() {
        waits = new Waits(driver);
        String ImageUrl = waits.waitForVisisblity(image,10).getAttribute("src");
        try {
            if(ImageUrl != null)
            {
                HttpURLConnection connection = (HttpURLConnection) (new URL(ImageUrl).openConnection());
                connection.setRequestMethod(("GET"));
                connection.connect();
                return connection.getResponseCode();
            } else{
                logger.info("Didnt catch source url ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public void headerLink(String linkName){
        try{
            for(WebElement element : headerList){
                if(element.getText().equalsIgnoreCase(linkName)){
                    element.click();
                }

            }
            throw new RuntimeException("Link with text '" + linkName + "' not found in header list!");
        } catch (Exception e) {
            logger.info("Error while navigating to: " + linkName + " - " + e.getMessage());
        }
    }

    public SearchPage searchItem(String item) {
        searchBox.sendKeys(item);
        searchButton.click();
        return new SearchPage(driver);
    }

    public HomePage searchEmptyItem(String item) {
        searchBox.sendKeys(item);
        searchButton.click();
        return new HomePage(driver);
    }

}
