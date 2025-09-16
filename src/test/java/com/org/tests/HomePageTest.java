package com.org.tests;

import com.org.base.BaseTest;
import com.org.pages.HomePage;
import com.org.utils.LoggerHelper;
import com.org.utils.configReader;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest{

    //Intilise Webdriver
    WebDriver driver;
    HomePage homePage;
    private static final Logger logger = LoggerHelper.getLogger(HomePageTest.class);

    //Run this before every test case to intilise driver
    @BeforeTest
    public void Setup(){
        driver = BaseTest.openApplication(configReader.getProperty("browser"),configReader.getProperty("baseUrl"));
        homePage = new HomePage(driver);
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
        BaseTest.closeApplication();
        logger.info("Browser closed successfully.");
    }

}
