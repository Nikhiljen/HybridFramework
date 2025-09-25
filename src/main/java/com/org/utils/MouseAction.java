package com.org.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseAction {
    private WebDriver driver;
    private Actions actions;

    public MouseAction(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    public void hover(WebElement element) {
        actions.moveToElement(element).perform();
    }

    public void click(WebElement element) {
        actions.moveToElement(element).click().perform();
    }

    public void hoverAndClick(WebElement elementToHover, WebElement elementToClick) {
        actions.moveToElement(elementToHover).pause(500).click(elementToClick).perform();
    }
}
