package com.org.tests;

import com.org.base.Base;
import com.org.pages.*;
import com.org.utils.AlertUtility;
import com.org.utils.LoggerHelper;
import com.org.utils.configReader;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class NavBarTest extends Base {
    private HomePage homePage;
    private static final Logger logger = LoggerHelper.getLogger(NavBarTest.class);

    @BeforeMethod(alwaysRun = true)
    public void Setup(){
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        Base.openApplication(configReader.getProperty("browser"),configReader.getProperty("baseUrl"));
        homePage = new HomePage();
    }

    @Test(groups = {"regression"})//
    public void testNavbarSubCategoryCheck(){
//        HomePage homePage = new HomePage();
        List<WebElement> parents = homePage.getParentsCategories();
        for(WebElement parent : parents){
            String parentText = parent.getText().trim();
            homePage.hoverAction(parent);
            List<WebElement> subs = homePage.getSubCategories(parent);
            if(!subs.isEmpty()){
                for(WebElement sub : subs){
                    String subText = sub.getText().trim();
                    homePage.clickAction(sub);
                    Assert.assertTrue(Base.getPageTitle().toLowerCase().contains(subText.toLowerCase()),"Navigation failed for subcategory: " + subText);
                    logger.info("Navigate from {} to sub branch page of {} is successfully" , parentText,subText);
                    Base.getDriver().navigate().back();
                    homePage.hoverAction(parent);
                }
            }else {
                homePage.clickAction(parent);
                Assert.assertTrue(Base.getPageTitle().toLowerCase().contains(parentText.toLowerCase()),"Navigation Failed for Parent Category" + parentText);
                logger.info("Navigate to {} page of is successfully" , parentText);
                Base.getDriver().navigate().back();
            }

        }
    }

    @AfterMethod(alwaysRun = true)
    public void closedBrowser(){
        Base.closeApplication();
        logger.info("Browser closed successfully.");
    }
}
