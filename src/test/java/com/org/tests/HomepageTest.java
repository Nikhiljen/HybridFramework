package com.org.tests;

import com.org.base.Base;
import com.org.pages.HomePage;
import com.org.pages.RegisterPage;
import com.org.utils.LoggerHelper;
import com.org.utils.configReader;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;


@Listeners(com.org.utility.TestListener.class)
public class HomepageTest extends Base {

    //Initialise
    HomePage homePage;
    RegisterPage registerpage;
    private static final Logger logger = LoggerHelper.getLogger(HomepageTest.class);

    //Run this before every test case to initialise driver
    @BeforeMethod
    public void Setup(){
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        Base.openApplication(configReader.getProperty("browser"),configReader.getProperty("baseUrl"));
        homePage = new HomePage(Base.getDriver());
        registerpage = new RegisterPage(Base.getDriver());
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

//    public void

    @Test
    public void testCase002_verifyRegisterPageNavigation(){
        homePage.headerLink("Register");
        Assert.assertTrue(registerpage.getPageTitle().contains("Register"));
        logger.info("Test is passed");
    }

    @AfterMethod
    public void closedBrowser(){
        Base.closeApplication();
        logger.info("Browser closed successfully.");
    }

}
