package com.org.pages;

import com.org.base.BaseTest;
import com.org.utils.LoggerHelper;
import com.org.utils.Waits;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class LoginPage extends BaseTest {
    public WebDriver driver;
    public WebElement radioButton = null;
    private static final Logger logger = LoggerHelper.getLogger(LoginPage.class);
    public static Waits waits;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "username")
    WebElement usernameFeild;

    @FindBy(xpath = "//*[@id='password']")
    WebElement passwordField;

    @FindBy(xpath = "//*[@id = 'usertype' and @value = 'admin']")
    WebElement Admin;
    @FindBy(xpath = "//*[@id = 'usertype' and @value='user']")
    WebElement User;

    @FindBy(xpath = "//*[@class = 'form-control' and @data-style = 'btn-info']")
    WebElement userRole;

    @FindBy(id = "terms")
    WebElement agreeCondtion;

    @FindBy(xpath = "//*[@id = 'signInBtn']")
    WebElement signButton;

    @FindBy(xpath = "//*[@id = 'okayBtn']")
    WebElement htmlModelAlert;


    //Method to do operations

    public void inputUsername(String userName){
        usernameFeild.clear();
        usernameFeild.sendKeys(userName);
    }

    public void inputPassword(String password){
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    //User selection With radio Button
    public void userType(String usertype){
        if(usertype.equalsIgnoreCase("admin")){
            radioButton = Admin;
        } else{
            radioButton = User;
        }

        if(radioButton != null){
            if(radioButton.isEnabled()){
                if(!radioButton.isSelected()){
                    radioButton.click();
                    logger.info("{} radio button selected.", usertype);
                } else{
                    logger.info("{} radio button is already Selected", usertype);
                }
            }else{
                logger.info("Radio Button is disabled");
            }
        }else{
            logger.info("{} Invalid UserType ", usertype);
        }
    }

        public void AcceptModelIfPresent(){
        try{
            waits = new Waits(driver);
            WebElement okayButton = waits.waitForClickable(htmlModelAlert,10);
            okayButton.click();
            logger.info("Alert has accepted successfully");

        } catch (Exception e) {
            logger.error("No alert present or error occurred while handling alert.", e);
        }
    }

    public void  selectUserRole(String role){
        if(userRole.isEnabled()){
            Select selectrole = new Select(userRole);
            selectrole.selectByVisibleText(role);
            logger.info(role + " selected from dropdown.");
        } else{
            logger.warn("User Role dropdown is disabled.");
        }
    }

    public String getuserRole(){
        Select select = new Select(userRole);
        return select.getFirstSelectedOption().getText();
    }

    public void termAndConditonCheckBox(boolean select){
        if(agreeCondtion.isEnabled())
            if(select && !(agreeCondtion.isSelected())){
            agreeCondtion.click();
            logger.info("Remember Me Agree condition checkbox selected.");
            } else if(!select && agreeCondtion.isSelected()){
            agreeCondtion.click();
            logger.info("Remember Me Agree condition checkbox deselected.");
            }else{
            logger.info("Remember Me Agree condition checkbox is desired condition" + select);
        }else {
                logger.info("Agree conditon is disbled");
        }
    }

    public HomePage SignButton(){
        try{
            signButton.click();
            logger.info("Sign Button clicked successfully.");
        } catch(Exception e){
            logger.error("Failed to click Sign Button.", e);
            throw e;
        }
        return new HomePage(driver);
    }


}
