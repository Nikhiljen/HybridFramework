package com.org.pages;

import com.org.base.Base;
import com.org.utils.LoggerHelper;
import com.org.utils.Waits;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class LoginPage extends Base {
    private WebDriver driver;
    private WebElement radioButton = null;
    private static final Logger logger = LoggerHelper.getLogger(LoginPage.class);
    private static Waits waits;

    public LoginPage(){
        PageFactory.initElements(getDriver(),this);
    }

}
