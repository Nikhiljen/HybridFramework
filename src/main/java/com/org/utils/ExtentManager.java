package com.org.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance(String filename){
        if(extent == null){
            createInstance(filename);
        }
        return extent;
    }

    private static void createInstance(String filename) {

        ExtentSparkReporter spark = new ExtentSparkReporter(filename);

        spark.config().setDocumentTitle("Automation Report");
        spark.config().setReportName("Test Results");
        spark.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }
}
