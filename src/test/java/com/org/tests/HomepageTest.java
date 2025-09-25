package com.org.tests;

import com.org.base.Base;
import com.org.pages.*;
import com.org.utility.DataGetter;
import com.org.utils.*;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;


@Listeners(com.org.utility.TestListener.class)
public class HomepageTest extends Base {

    //Initialise
    HomePage homePage;
    RegisterPage registerpage;
    SearchPage searchPage;
    LoginPage loginPage;
    AlertUtility alert;
    ShoppingCartPage shoppingCartPage;
    private static final Logger logger = LoggerHelper.getLogger(HomepageTest.class);

    //Run this before every test case to initialise driver
    @BeforeMethod
    public void Setup(){
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        Base.openApplication(configReader.getProperty("browser"),configReader.getProperty("baseUrl"));
        homePage = new HomePage();
        registerpage = new RegisterPage();
        searchPage = new SearchPage();
        loginPage = new LoginPage();
        alert = new AlertUtility(Base.getDriver());
        shoppingCartPage = new ShoppingCartPage();
    }

    @Test
    public void testCase001_verifyHomePageLogoImage(){
        try{
            int responseCode = homePage.ImageProcessor();
            Assert.assertEquals(responseCode,200);
            logger.info("Test Case 101 PASSED: Response code is {}", responseCode);
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
        LoginPage loginPage = (LoginPage) homePage.headerLink("Log in");
        Assert.assertTrue(loginPage.getPageTitle().contains(configReader.getProperty("loginTitleName")));
        logger.info("Login Page Test is passed");
    }

    @Test
    public void testCase003_verifyRegisterPageNavigation(){
        RegisterPage registerPage = (RegisterPage) homePage.headerLink("Register");
        Assert.assertTrue(registerPage.getPageTitle().contains(configReader.getProperty("registerTitleName")));
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
                        searchPage.getErrorMessage().contains(configReader.getProperty("errorMessage")),
                                "Invalid search did not land on search page");
                logger.info("Search Page INVALID test passed for item: {}", itemName);
            }

            case NO_PRODUCT ->{
                homePage = homePage.searchEmptyItem(itemName);
                String errorMessage = alert.acceptAndReturnMessage();
                Assert.assertEquals(errorMessage,configReader.getProperty("emptyItemMessage"));
                logger.info("Search Page with no Product name test passed for item: {}", itemName);
            }

        }
    }

    @Test
    public void testCase005_verifyShoppingCartWithDefaultCountItem(){
        Assert.assertEquals(homePage.getCartCount(),0,"Initial cart count should be 0");
        logger.info("Default zero item in Shopping Cart Page.......Test is passed");
    }

    @Test
    public void testCase006_verifyShoppingCartWithItemAddInCast(){
        homePage.AddProductToCart();
        Assert.assertEquals(homePage.getCartCount(),1,"cart count should be 1");
        logger.info("After Adding item in Shopping Cart Page.......Test is passed");
    }

    public void testCase007_verifyNavBarLinks(){
        List<WebElement> parents = homePage.getParentsCategories();
        for(WebElement parent : parents){
            String parentText = parent.getText().trim();
            homePage.hoverAction(parent);
            List<WebElement> subs = homePage.getSubCategories(parent);


        }
    }



    @AfterMethod
    public void closedBrowser(){
        Base.closeApplication();
        logger.info("Browser closed successfully.");
    }

}
