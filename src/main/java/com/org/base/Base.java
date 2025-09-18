package com.org.base;

import com.org.utils.Waits;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Base {
    public static WebDriver driver;
    public static Waits waits;


    public static WebDriver openApplication(String browser, String url){
        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if(browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }else if(browser.equalsIgnoreCase("edge")){
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if(browser.equalsIgnoreCase("safari")){
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }

        driver.manage().window().maximize();
        //Open base url of application
        driver.get(url);

        //wait for application to load
        waits = new Waits(driver);
        waits.setImplicitwait(10);
        waits.setPageLoadtime(10);
        return driver;

    }

    public static void closeApplication(){
        if(driver != null){
            driver.quit();

        }
    }

    public static String CapturScreen (String testName){
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filePath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timestamp + ".png";

        try{
            TakesScreenshot ts = (TakesScreenshot) driver;
            File Source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);

            FileUtils.copyFile(Source,destination);
            System.out.println("Screenshot saved at: " + filePath);
        }catch (IOException e){
            System.out.println("Screenshot saved at: " + filePath);
        }

        return filePath;
    }
}
