package com.org.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.org.base.Base;
import com.org.utils.ExtentManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class TestListener extends Base implements ITestListener{

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String outputPath = System.getProperty("user.dir") + "/test-output";
        File outputDir = new File(outputPath);
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        String fileName = outputPath + "/ExtentReport-" + System.currentTimeMillis() + ".html";
        extent = ExtentManager.getInstance(fileName);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(result.getName() + " Test failed");
        test.get().fail(result.getThrowable());

        //Capture ScreenShot
        String screenshotPath = Base.CapturScreen(result.getMethod().getMethodName());
        if (screenshotPath != null){
        try {
            test.get().addScreenCaptureFromPath(screenshotPath, "Failed TestCase");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
        }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getName() + " Test Skipped");
        test.get().skip("Test skipped");

        String screenshotPath = Base.CapturScreen(result.getMethod().getMethodName());

        if (screenshotPath != null){
        try {
            test.get().addScreenCaptureFromPath(screenshotPath, "Skipped TestCase");
        }catch (Exception e){
            e.printStackTrace();
        }
    }}

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}

