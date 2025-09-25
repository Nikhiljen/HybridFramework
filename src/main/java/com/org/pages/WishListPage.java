package com.org.pages;

import com.org.base.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class WishListPage extends Base {

    public WishListPage() {
        PageFactory.initElements(getDriver(), this);

    }
}
