package com.org.utils;

import com.org.base.Base;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class Waits extends Base {
    private WebDriver driver;

    //initilise driver
    public Waits(WebDriver driver){
        this.driver = driver;
    }

    //Implicit wait:Applies to all findElement calls
    public void setImplicitWait(long second){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(second));
    }

    //Explicit wait:Wait until the element is visible
    public WebElement waitForVisibility(WebElement locator, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOf(locator));
    }

    public WebElement waitForClickable(WebElement locator, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }



    public void waitForCategoryPage(String categoryName, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//div[@class='page-title']/h1"),categoryName));
    }


    public void setPageLoadtime(long timeoutInSeconds){
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeoutInSeconds));
    }

    public Alert alertIsPresent(long timeoutInSeconds){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException e) {
            return null; // no alert found
        }
    }

    public void waitForCartCountToBe(WebElement cartCountSpan, int expectedCount, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(driver -> {
            String text = cartCountSpan.getText(); // e.g., "(1)"
            text = text.replaceAll("[^0-9]", "");
            int count = Integer.parseInt(text);
            return count == expectedCount;
        });
    }
}
