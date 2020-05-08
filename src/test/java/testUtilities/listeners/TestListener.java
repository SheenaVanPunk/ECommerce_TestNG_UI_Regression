package testUtilities.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import classesUtilities.StepsLogger;
import testClasses.BaseTest;
import testUtilities.extentReportsClasses.ExtentReporterManager;
import testUtilities.extentReportsClasses.ExtentTestManager;

import java.io.File;
import java.io.IOException;


public class TestListener extends BaseTest implements ITestListener {
     StepsLogger log = new StepsLogger();

     private static ThreadLocal<ExtentTest> parallel = new ThreadLocal<ExtentTest>();

    @Override
    public void onStart(ITestContext context) {
        System.out.println("*** Test Suite " + context.getName() + " started ***");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Failed test methods:" + context.getFailedTests());
        System.out.println("Execution started at: " + context.getStartDate() +
                            "\nExecution ended at: " + context.getEndDate());

        float lenghtOfExecution = (context.getEndDate().getTime()
                                    - context.getStartDate().getTime()) / 1000;
        System.out.println("Execution lasted for: " + lenghtOfExecution  + " seconds.");
        System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
        ExtentTestManager.endTest();
        ExtentReporterManager.getInstance().flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..." +
                "\nTEST STEPS"));

        parallel.set(ExtentTestManager.startTest(result.getTestClass().getName() + " | " + result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");

       parallel.get().log(Status.PASS, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
        parallel.get().log(Status.FAIL, "FAILED");
        parallel.get().log(Status.FAIL, result.getThrowable());

        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();

        try {
            parallel.get().addScreenCaptureFromPath(getScreenshotPath(result.getMethod().getMethodName(), driver), result.getMethod().getMethodName());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
        ExtentTestManager.getTest().log(Status.SKIP, "SKIPPED");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }



}
