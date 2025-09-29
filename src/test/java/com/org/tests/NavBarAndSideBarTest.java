package com.org.tests;

import com.org.base.Base;
import com.org.pages.*;
import com.org.utils.LoggerHelper;
import com.org.utils.configReader;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class NavBarAndSideBarTest extends Base {
    private HomePage homePage;
    private static final Logger logger = LoggerHelper.getLogger(NavBarAndSideBarTest.class);

    @BeforeMethod(alwaysRun = true)
    public void Setup(){
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        Base.openApplication(configReader.getProperty("browser"),configReader.getProperty("baseUrl"));
        homePage = new HomePage();
    }

    @Test(groups = {"regression"})//
    public void testNavbarSubCategoryCheck(){
//        HomePage homePage = new HomePage();
          List<WebElement> ParentsCategoryList = homePage.getParentsCategories();
          for(WebElement parentList : ParentsCategoryList){
            String parentText = parentList.getText().trim();
            homePage.hoverAction(parentList);
            List<WebElement> subs = homePage.getSubCategories(parentList);
            if(!subs.isEmpty()){
                for(WebElement sub : subs){
                    String subText = sub.getText().trim();
                    homePage.clickAction(sub);
                    Assert.assertTrue(Base.getPageTitle().toLowerCase().contains(subText.toLowerCase()),"Navigation failed for subcategory: " + subText);
                    logger.info("Navigate from Main NaveBar {} to sub branch page of {} is successfully" , parentText,subText);
                    Base.getDriver().navigate().back();
                    homePage.hoverAction(parentList);
                }
            }else {
                homePage.clickAction(parentList);
                Assert.assertTrue(Base.getPageTitle().toLowerCase().contains(parentText.toLowerCase()),"Navigation Failed for Parent Category" + parentText);
                logger.info("Navigate to Main {} page is successfully" , parentText);
                Base.getDriver().navigate().back();
            }

        }
    }

    @Test(groups = {"regression"})
    public void testSideNavBarCheck(){
        List<WebElement> ParentsCategoryList= homePage.getSideParentsCategories();

        for(WebElement parentElement : ParentsCategoryList){
            String parentText = parentElement.getText().trim();
            homePage.hoverAction(parentElement);
            getWaits().waitForClickable(parentElement, 30).click();
            getWaits().waitForCategoryPage(parentText, 30);
            List<WebElement> childElementsList = homePage.getSubCategories(parentElement);
            if(!childElementsList.isEmpty()){
                for(WebElement childElement : childElementsList){
                    String subText = childElement.getText().trim();
                    homePage.hoverAction(childElement);
                    getWaits().waitForClickable(childElement, 30).click();
                    Assert.assertTrue(Base.getPageTitle().toLowerCase().contains(subText.toLowerCase()),"Navigation failed for subcategory: " + subText);
                    logger.info("Navigate SideBar of {} to sub branch page of {} is successfully" , parentText,subText);
                    Base.getDriver().navigate().back();
                    homePage.hoverAction(parentElement);

                }
            }else {
                Assert.assertTrue(
                        Base.getPageTitle().toLowerCase().contains(parentText.toLowerCase()),
                        "Navigation Failed for Parent Category " + parentText
                );
                logger.info("Navigated SideBar Main Page {} successfully", parentText);
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
