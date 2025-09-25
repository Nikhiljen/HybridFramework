package com.org.utils;

import com.org.base.Base;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

public class AlertUtility extends Base {
    private WebDriver driver;
    private Waits wait;

    public AlertUtility(WebDriver driver){
        this.driver = driver;
    }


    public String acceptAndReturnMessage(){
        try {
            wait = new Waits(driver);
            Alert alert = wait.alertIsPresent(10);

            String Message = alert.getText();
            alert.accept();
            return Message;
        } catch (Exception e){
            return null; // No alert appeared
        }
    }

    public void acceptAlert(int timeoutInSeconds) {
        try {
            wait = new Waits(driver);
            Alert alert = wait.alertIsPresent(10);
            alert.accept();
        } catch (Exception ignored) {}
    }

    /**
     * Dismiss alert if it appears
     */
    public void dismissAlert(int timeoutInSeconds) {
        try {
            wait = new Waits(driver);
            Alert alert = wait.alertIsPresent(10);
            alert.dismiss();
        } catch (Exception ignored) {}
    }


}
