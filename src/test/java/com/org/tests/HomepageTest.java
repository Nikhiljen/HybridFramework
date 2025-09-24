package com.org.tests;

import com.org.base.Base;
import com.org.pages.*;
import com.org.utility.DataGetter;
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
    LoginPage loginPage;
    AlertUtility alert;
    private static final Logger logger = LoggerHelper.getLogger(HomepageTest.class);

    //Run this before every test case to initialise driver
    @BeforeMethod
    public void Setup(){
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        Base.openApplication(configReader.getProperty("browser"),configReader.getProperty("baseUrl"));
        homePage = new HomePage(Base.getDriver());
        registerpage = new RegisterPage(Base.getDriver());
        searchPage = new SearchPage(Base.getDriver());
        loginPage = new LoginPage(Base.getDriver());
        alert = new AlertUtility(Base.getDriver());

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
        Assert.assertTrue(loginPage.getPageTitle().contains(configReader.getProperty("loginTitleName")));
        logger.info("Login Page Test is passed");
    }

    @Test
    public void testCase003_verifyRegisterPageNavigation(){
        homePage.headerLink("Register");
        Assert.assertTrue(registerpage.getPageTitle().contains(configReader.getProperty("registerTitleName")));
        logger.info("Register Navigation Test is passed");
    }

    @Test(dataProvider = "SearchItem", dataProviderClass = DataGetter.class)
    public void testCase004_verifySearchBoxValidationWithValid_Invalid_NoProductName(String itemName, TypeOfValidation type){

        switch (type){
            case VALID ->{
                searchPage = homePage.searchItem(itemName);
                Assert.assertTrue(
                        searchPage.getPageTitle().contains(configReader.getProperty("SearchTitleName")),
                        "Valid search did not navigate to Search Results page");
                logger.info("Search Page VALID test passed for item: {}", itemName);
            }

            case INVALID ->{
                searchPage = homePage.searchItem(itemName);
                Assert.assertTrue(
                        searchPage.getErrorMessage().contains(configReader.getProperty("errormessage")),
                                "Invalid search did not land on search page");
                logger.info("Search Page INVALID test passed for item: {}", itemName);
            }

            case NO_PRODUCT ->{
                homePage = homePage.searchEmptyItem(itemName);
                String errorMessage = alert.acceptAndReturnMessage();
                Assert.assertEquals(errorMessage,configReader.getProperty("emptyitemMessage"));
                logger.info("Search Page with no Product name test passed for item: {}", itemName);
            }

        }
    }

//    @Test
//    public void testCase005_verifySearchBoxValidationWithValid_Invalid_NoProductName(){
//        homePage.insertItemInSearchBar(configReader.getProperty("validItemName"));
//        Assert.assertTrue(searchPage.getPageTitle().contains(configReader.getProperty("SearchTitleName")));
//        logger.info("Search Page Test is passed");
//    }



    @AfterMethod
    public void closedBrowser(){
        Base.closeApplication();
        logger.info("Browser closed successfully.");
    }

}
