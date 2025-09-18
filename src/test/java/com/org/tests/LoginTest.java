package com.org.tests;

import com.org.base.Base;
import com.org.pages.HomePage;
import com.org.pages.LoginPage;
import com.org.utils.LoggerHelper;
import com.org.utils.configReader;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest extends Base {

    WebDriver driver;
    LoginPage loginPage;
    private static final Logger logger = LoggerHelper.getLogger(LoginTest.class);

    //Run this before every test case to initialise driver
    @BeforeTest
    public void Setup(){
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        driver = Base.openApplication(configReader.getProperty("browser"),configReader.getProperty("baseUrl"));
        loginPage = new LoginPage(driver);
    }

    @Test
    public void TestCase_101(){
        String userName = configReader.getProperty("username");
        String passWord = configReader.getProperty("password");
        String usertype = configReader.getProperty("user_type");
        String userRole = configReader.getProperty("user_role");

        try{
            //Enter username and password
            loginPage.inputUsername(userName);
            loginPage.inputPassword(passWord);

            //Enter role
            loginPage.userType(usertype);

            //Select User or admin role
            loginPage.selectUserRole(userRole);
            loginPage.AcceptModelIfPresent();

            //from user select role which you want
            String selectRole = loginPage.getuserRole();
            Assert.assertEquals(selectRole, userRole,"Dropdown selection failed!");
            logger.info("Dropdown selection verified: {}", selectRole);

            //click on term and condition checkbox
            loginPage.termAndConditonCheckBox(true);

            //click on sign button and jump on home page
            HomePage homepage = loginPage.SignButton();
            logger.info("User Jump to HomePage of application", selectRole);

        } catch (AssertionError ae) {
            logger.error("TestCase_101 FAILED: Assertion failed", ae);
            throw ae;  // rethrow to let TestNG handle failure
        } catch (Exception e) {
            logger.error("TestCase_101 FAILED: Unexpected exception occurred", e);
            Assert.fail("TestCase_101 FAILED due to exception: " + e.getMessage());
        }
    }

    public void TestCase_102(){
        String userName = configReader.getProperty("username");
        String passWord = configReader.getProperty("password");
        String usertype = configReader.getProperty("user_type");
        String userRole = configReader.getProperty("user_role");

        try{
            //Enter username and password
            loginPage.inputUsername(userName);
            loginPage.inputPassword(passWord);

            //Enter role
            loginPage.userType(usertype);

            //Select User or admin role
            loginPage.selectUserRole(userRole);
            loginPage.AcceptModelIfPresent();

            //from user select role which you want
            String selectRole = loginPage.getuserRole();
            Assert.assertEquals(selectRole, userRole,"Dropdown selection failed!");
            logger.info("Dropdown selection verified: {}", selectRole);

            //click on term and condition checkbox
            loginPage.termAndConditonCheckBox(true);

            //click on sign button and jump on home page
            HomePage homepage = loginPage.SignButton();
            logger.info("User Jump to HomePage of application", selectRole);

        } catch (AssertionError ae) {
            logger.error("TestCase_101 FAILED: Assertion failed", ae);
            throw ae;  // rethrow to let TestNG handle failure
        } catch (Exception e) {
            logger.error("TestCase_101 FAILED: Unexpected exception occurred", e);
            Assert.fail("TestCase_101 FAILED due to exception: " + e.getMessage());
        }
    }
    @AfterMethod
    public void closedBrowser(){
        Base.closeApplication();
        logger.info("Browser closed successfully.");
    }
}
