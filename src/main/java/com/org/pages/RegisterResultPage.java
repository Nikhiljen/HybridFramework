package com.org.pages;

import com.org.base.Base;
import com.org.utils.LoggerHelper;
import com.org.utils.Waits;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterResultPage extends Base {
    private static final Logger logger = LoggerHelper.getLogger(RegisterPage.class);

    public RegisterResultPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(xpath = "//*[@class='result']")
    private WebElement resultText;

    @FindBy(xpath = "//*[contains(@class,'register-continue-button')]")
    private WebElement continueButton;

    //Validation on right page
    public boolean isMessageDisplay(){
        try{
            return getWaits().waitForVisisblity(resultText,5).isDisplayed();
        }catch (Exception e) {
            return false;
        }
    }

    //Navigate to homepage
    public HomePage clickContinueButton(){
        try{
            continueButton.click();
            logger.info("Clicked on Continue button, navigating to Home Page.");
            return new HomePage();
        } catch (Exception e) {
            logger.error("Failed to click Continue button or navigate to Home Page.", e);
            return null;
        }
    }

}
