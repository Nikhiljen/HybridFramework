package com.org.pages;

import com.org.utils.LoggerHelper;
import com.org.utils.Waits;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {
    WebDriver driver;
    public static Waits waits;
    private static final Logger logger = LoggerHelper.getLogger(HomePage.class);


    //   used PageFactory method to avoid stale element exceptions
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle(){
        return driver.getTitle();
    }
}
