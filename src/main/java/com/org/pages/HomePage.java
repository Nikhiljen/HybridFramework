package com.org.pages;

import com.org.base.Base;
import com.org.utils.LoggerHelper;
import com.org.utils.MouseAction;
import com.org.utils.Waits;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage extends Base {
    private static final Logger logger = LoggerHelper.getLogger(HomePage.class);
    private MouseAction mouseAction;


//   used PageFactory method to avoid stale element exceptions
    public HomePage() {
        PageFactory.initElements(getDriver(), this);
        mouseAction = new MouseAction(getDriver());
    }

    //Get all locator here

    @FindBy(xpath = "//*[@class='header-logo']//img")
    private WebElement image;

    @FindBy(xpath= "//*[@class='header-links']//a |//*[@class='header-links']//a/span")
    private List<WebElement> headerList;

    @FindBy(xpath = "//*[@id='small-searchterms']")
    private WebElement searchBox;

    @FindBy(xpath = "//*[contains(@class,'search-box-button')]")
    private WebElement searchButton;

    @FindBy(xpath = "//a[normalize-space(text())='14.1-inch Laptop']/parent::h2/following-sibling::div[3]/child::div[2]/input")
    private WebElement productAddToCartButton;

    @FindBy(css = "span.cart-qty")
    private WebElement cartCountSpan;

    @FindBy(xpath = "//ul[@class='top-menu']/li[not(ancestor::ul[@class!='top-menu'])]")
    private List<WebElement> navBarElement;

    //Method to check for homepage
    public int ImageProcessor() {
        String ImageUrl = getWaits().waitForVisisblity(image,10).getAttribute("src");
        try {
            if(ImageUrl != null)
            {
                HttpURLConnection connection = (HttpURLConnection) (new URL(ImageUrl).openConnection());
                connection.setRequestMethod(("GET"));
                connection.connect();
                return connection.getResponseCode();
            } else{
                logger.info("Didnt catch source url ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }


    //Method to check if header link will work or not
    public Base headerLink(String linkName){
        try{
            for(WebElement element : headerList){
                if(element.getText().equalsIgnoreCase(linkName)){
                    element.click();
                    logger.info("Clicked on header link: {}", linkName);

                    // Return appropriate page object
                    return switch (linkName.toLowerCase()) {
                        case "log in" -> new LoginPage();
                        case "register" -> new RegisterPage();
                        case "shopping cart" -> new ShoppingCartPage();
                        default -> new WishListPage();
                    };
                }
            }
            throw new RuntimeException("Link with text '" + linkName + "' not found in header list!");
        } catch (Exception e) {
            logger.info("Error while navigating to: {} - {}", linkName, e.getMessage());
            throw e;
        }
    }


    // Search bar method
    public SearchPage searchItem(String item) {
        searchBox.clear();
        searchBox.sendKeys(item);
        searchButton.click();
        return new SearchPage();
    }

    public HomePage searchEmptyItem(String item) {
        searchBox.clear();
        searchBox.sendKeys(item);
        searchButton.click();
        return new HomePage();
    }


    // Shopping cart method to check item add or not
    public void AddProductToCart(){
        int initialCount = getCartCount();
        productAddToCartButton.click();

        int expectedCount = initialCount + 1;
        getWaits().waitForCartCountToBe(cartCountSpan,expectedCount,10);

    }

    public int getCartCount(){
        String text = cartCountSpan.getText();
        text = text.replaceAll("[^0-9]","");
        return Integer.parseInt(text);
    }

    //Method to check Navbar link working and landing on desired web page
    public List<WebElement> getParentsCategories(){
        return navBarElement;
    }

    public List<WebElement> getSubCategories(WebElement parent){
        return parent.findElements(By.xpath(".//li/a"));
    }

    public void hoverAction(WebElement parent){
        mouseAction.hover(parent);
    }


}
