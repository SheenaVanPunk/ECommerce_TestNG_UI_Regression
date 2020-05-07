package testUtilities.listeners;

import com.aventstack.extentreports.Status;
import org.testng.*;
import classesUtilities.StepsLogger;
import testUtilities.extentReportsClasses.ExtentReporterManager;
import testUtilities.extentReportsClasses.ExtentTestManager;


public class TestListener implements ITestListener {
     StepsLogger log = new StepsLogger();

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
        ExtentTestManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
        ExtentTestManager.getTest().log(Status.PASS, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
        ExtentTestManager.getTest().log(Status.FAIL, "FAILED");

//        var camera = (TakesScreenshot) driver;
//        File screenshot = camera.getScreenshotAs(OutputType.FILE);
//        try {
//            Files.move(screenshot, new File("resources/failedTestScreenshots/" + result.getName() + ".png"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            test.get().fail("details").addScreenCaptureFromPath("resources/failedTestScreenshots/");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
