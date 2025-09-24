package com.org.tests;

import com.org.base.Base;
import com.org.pages.*;
import com.org.utils.*;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;


@Listeners(com.org.utility.TestListener.class)
public class HomepageTest extends Base {

    //Initialise
    HomePage homePage;
    RegisterPage registerpage;
    SearchPage searchPage;
    private static final Logger logger = LoggerHelper.getLogger(HomepageTest.class);

    //Run this before every test case to initialise driver
    @BeforeMethod
    public void Setup(){
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        Base.openApplication(configReader.getProperty("browser"),configReader.getProperty("baseUrl"));
        homePage = new HomePage(Base.getDriver());
        registerpage = new RegisterPage(Base.getDriver());
        searchPage = new SearchPage(Base.getDriver());

    }

    @Test
    public void testCase001_verifyHomePageLogoImage(){
        try{
            int responseCode = homePage.ImageProcessor();
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

    @Test
    public void testCase002_verifyLogInPageNavigation(){
        homePage.headerLink("Log in");
        Assert.assertTrue(registerpage.getPageTitle().contains("Welcome, Please Sign In!"));
        logger.info("Login Page Test is passed");
    }

    @Test
    public void testCase003_verifyRegisterPageNavigation(){
        homePage.headerLink("Register");
        Assert.assertTrue(registerpage.getPageTitle().contains(configReader.getProperty("registerTitleName")));
        logger.info("Register Navigation Test is passed");
    }

    public void testCase004_verifySearchBoxValidationWithValid_Invalid_NoProductName(){
        homePage.insertItemInSearchBar(configReader.getProperty("validItemName"));
        Assert.assertTrue(registerpage.getPageTitle().contains(configReader.getProperty("SearchTitleName")));
        logger.info("Search Page Test is passed");
    }



    @AfterMethod
    public void closedBrowser(){
        Base.closeApplication();
        logger.info("Browser closed successfully.");
    }

}
