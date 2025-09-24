package com.org.pages;

import com.org.base.Base;
import com.org.utils.LoggerHelper;
import com.org.utils.TypeOfValidation;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RegisterPage extends Base {
    WebDriver driver;
    private static final Logger logger = LoggerHelper.getLogger(RegisterPage.class);

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    //locators
    @FindBy(xpath = "//*[@name='Gender']")
    private List<WebElement> genderOfUser;

    //Firstname column
    @FindBy(xpath = "//*[contains(@id,'FirstName')]")
    private WebElement firstName;

    @FindBy(xpath = "//*[starts-with(@id,'LastName')]")
    private WebElement lastName;

    @FindBy(xpath = "//*[contains(@id,'Email')]")
    private WebElement email;

    @FindBy(id = "Password")
    private WebElement password;

    @FindBy(id = "ConfirmPassword")
    private WebElement confirmPassword;

    @FindBy(xpath = "//*[contains(@id,'register-button')]")
    private WebElement registerButton;

    @FindBy(xpath = "//*[@class='field-validation-error']/span")
    private WebElement WrongEmailPatternMessage;

    @FindBy(xpath = "//*[contains(@class,'validation-summary-errors')]//li")
    private WebElement duplicateEmailMessage;


   //Method
    public String getPageTitle(){
            return driver.getTitle();
    }

    public void selectUserGender(String gender){
        if (genderOfUser == null || genderOfUser.isEmpty()) {
            logger.info("No gender radio buttons found!");
            return;
        }
        boolean found  = false;

        for(WebElement option : genderOfUser){
            String value = option.getAttribute("value");
            if(value != null && value.equalsIgnoreCase(gender)){
                if(option.isEnabled() && !option.isSelected()){
                    option.click();
                    logger.info("{} radio button is selected", gender);
                } else if(option.isSelected()){
                    logger.info("{} radio button is already selected", gender);

                } else {
                    logger.info("{} radio button is disabled", gender);
                }

                found = true;
                break;
            }
        }
        if(!found){
            logger.info("{} is an invalid user type.", gender);
        }

    }

    public void fillFirstNameField(String name){
        firstName.sendKeys(name);
        logger.info("{} first name is field Successfully", name);
    }

    public void fillLastNameField(String name){
        lastName.sendKeys(name);
        logger.info("{} Last name is field Successfully", name);
    }

    public void fillEmailIdField(String emailId){
        email.sendKeys(emailId);
        logger.info("{} Email Id  is field Successfully", emailId);
    }

    public void fillPasswordField(String pass){
        password.sendKeys(pass);
        logger.info("{} Password is field Successfully", pass);
    }

    public void fillConfirmPasswordField(String pass){
        confirmPassword.sendKeys(pass);
        logger.info("{} Cofirm password is field Successfully", pass);
    }

    public RegisterResultPage clickOnRegisterButton(){
        try{
            registerButton.click();
            logger.info("Register Button clicked successfully.");
            return new RegisterResultPage(driver);
        } catch(Exception e){
            logger.error("Failed to click Register Button.", e);
            throw e;
        }
    }

    public String getErrorMessage(TypeOfValidation message){

        switch(message){
            case INVALID:
                return WrongEmailPatternMessage.getText();

            case DUPLICATE:
                return duplicateEmailMessage.getText();

            default:
                return "No Message";
        }
    }




}
