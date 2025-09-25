package com.org.pages;

import com.org.base.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ShoppingCartPage extends Base {

    public ShoppingCartPage() {
        PageFactory.initElements(getDriver(), this);
    }
}

