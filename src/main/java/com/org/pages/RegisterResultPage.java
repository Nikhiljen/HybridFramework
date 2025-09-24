package com.org.pages;

import com.org.utils.LoggerHelper;
import com.org.utils.Waits;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterResultPage {
    WebDriver driver;
    public static Waits waits;
    private static final Logger logger = LoggerHelper.getLogger(RegisterPage.class);

    public RegisterResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waits = new Waits(driver);
    }

    @FindBy(xpath = "//*[@class='result']")
    private WebElement resultText;

    @FindBy(xpath = "//*[contains(@class,'register-continue-button')]")
    private WebElement continueButton;

    //Validation on right page
    public boolean isMessageDisplay(){
        try{
            return waits.waitForVisisblity(resultText,5).isDisplayed();
        }catch (Exception e) {
            return false;
        }
    }

    //Navigate to homepage
    public HomePage clickContinueButton(){
        try{
            continueButton.click();
            logger.info("Clicked on Continue button, navigating to Home Page.");
            return new HomePage(driver);
        } catch (Exception e) {
            logger.error("Failed to click Continue button or navigate to Home Page.", e);
            return null;
        }
    }

}
