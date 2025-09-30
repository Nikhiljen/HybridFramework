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
import java.util.stream.Collectors;

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


    //Test method for Side Navbar
    @Test
    public void testSideNavBar(){
        List<String> parentTitles = homePage.getSideParentsCategories()
                .stream()
                .map(WebElement::getText)
                .toList();

        for(String parentText : parentTitles){
            // 2. RE-FETCH the PARENT ELEMENT to click and navigate
            WebElement parentElement = homePage.getParentCategoryByText(parentText);

            //Now perform the navigation, which makes the parentElement stale

            homePage.clickAction(parentElement);
            getWaits().waitForCategoryPage(parentText,10);

            Assert.assertTrue(
                    Base.getPageTitle().toLowerCase().contains(parentText.toLowerCase()),
                    "Navigation Failed for Parent Category " + parentText
            );
            logger.info("Navigated to Parent Page: {} successfully", parentText);

            // 3. Collect CHILDREN on the destination page
            List<WebElement> subs = homePage.getSubCategoryListOnPage();

            List<String> subTitles = subs.stream()
                    .map(WebElement::getText)
                    .toList();

            if(!subTitles.isEmpty()){
                for(String subText : subTitles){
                    // 5. RE-FETCH the CHILD ELEMENT on the current page to click
                    WebElement subElement = homePage.getSubCategoryByTextOnPage(subText);
                    homePage.clickAction(subElement); // Navigate away

                    getWaits().waitForCategoryPage(subText,10);
                    Assert.assertTrue(Base.getPageTitle().toLowerCase().contains(subText.toLowerCase()),"Navigation failed for subcategory: " + subText);
                    logger.info("Navigate from Main NaveBar {} to sub branch page of {} is successfully" , parentText,subText);
                    // 8. CRUCIAL: Navigate back to the parent page to re-test the next child
                    Base.getDriver().navigate().back();
                    getWaits().waitForCategoryPage(parentText,10); // Wait for the Parent page to reload
                    // --- END OF INNER LOOP ---
                }
            }

            // 9. Navigate back to the home page to start the next Parent category's test
            Base.getDriver().navigate().back();
        }

    }

    @AfterMethod(alwaysRun = true)
    public void closedBrowser(){
        Base.closeApplication();
        logger.info("Browser closed successfully.");
    }
}

