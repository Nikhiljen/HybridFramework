package com.org.base;

import com.org.utils.Waits;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Base {
    public static Waits waits;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }


    public static WebDriver openApplication(String browser, String url){

        WebDriver drv = null;


        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");  // Run Chrome in headless mode
            options.addArguments("--window-size=1920,1080"); // Set screen size
            drv = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless"); // Run Firefox in headless mode
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
            drv = new FirefoxDriver(options);

        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless=new"); // Headless Edge
            options.addArguments("--window-size=1920,1080");
            drv = new EdgeDriver(options);

        } else if (browser.equalsIgnoreCase("safari")) {
            // ❌ Safari does not support headless mode
            WebDriverManager.safaridriver().setup();
            drv = new SafariDriver();
        }

        // Store this driver in ThreadLocal
        driver.set(drv);

        drv.manage().window().maximize();
        //Open base url of application
        drv.get(url);

        //wait for application to load
        waits = new Waits(drv);
        waits.setImplicitwait(10);
        waits.setPageLoadtime(10);
        return drv;

    }

    public static void closeApplication(){
        if(getDriver() != null){
            getDriver().quit();
            driver.remove();

        }
    }

    public static String CapturScreen (String testName){
        WebDriver drv = getDriver();

        if (drv == null) {
            System.out.println("⚠️ WebDriver is null, cannot take screenshot");
            return null;
        }
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filePath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timestamp + ".png";

        try{
            TakesScreenshot ts = (TakesScreenshot) drv;
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
