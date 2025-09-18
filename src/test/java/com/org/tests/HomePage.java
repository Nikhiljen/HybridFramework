package com.org.tests;

import com.org.base.Base;
import com.org.utils.LoggerHelper;
import com.org.utils.configReader;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HomePage extends Base {

    //Intilise Webdriver
    WebDriver driver;
    com.org.pages.HomePage homePage;
    private static final Logger logger = LoggerHelper.getLogger(HomePage.class);

    //Run this before every test case to intilise driver
    @BeforeTest
    public void Setup(){
        driver = Base.openApplication(configReader.getProperty("browser"),configReader.getProperty("baseUrl"));
        homePage = new com.org.pages.HomePage(driver);
    }

    @Test
    public void teseCase101(){
        try{
            int responseCode = homePage.sendlogoImage();
            Assert.assertEquals(responseCode,200);
            logger.info("Test Case 101 PASSED: Response code is " + responseCode);
        }catch (AssertionError ae) {
            logger.error("Test Case 101 FAILED: Assertion failed", ae);
            throw ae; // re-throw to let the test framework handle it
        } catch (Exception e) {
            logger.error("Test Case 101 FAILED: Unexpected error occurred", e);
            Assert.fail("Test Case 101 FAILED due to an exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void closedBrowser(){
        Base.closeApplication();
        logger.info("Browser closed successfully.");
    }

}
