package com.org.utils;

import com.org.base.Base;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class Waits extends Base {
    private WebDriver driver;

    //initilise driver
    public Waits(WebDriver driver){
        this.driver = driver;
    }

    //Implicit wait:Applies to all findElement calls
    public void setImplicitwait(long second){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(second));
    }

    //Explicit wait:Wait until the element is visible
    public WebElement waitForVisisblity(WebElement locator, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOf(locator));
    }

    public WebElement waitForClickable(WebElement locator, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    //fluent wait: polling for every few seconds and ignoring exceptions
    public WebElement fluentWait(final By locator, long timeoutInSeconds, long pollingInMills){
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofSeconds(pollingInMills))
                .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(locator);
            }
        });


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

}
