package testUtilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testClasses.BaseTest;

import java.io.IOException;


public class TestListener extends BaseTest implements ITestListener {
    private static ExtentReports extent = ExtentReporterManager.createInstance();
    public static ThreadLocal<ExtentTest> extentParallel = new ThreadLocal<ExtentTest>();


    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    private static String getTestClassName(ITestResult iTestResult){
        return iTestResult.getTestClass().getRealClass().getSimpleName();
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("*** Test Suite " + context.getName() + " started ***");

    }

    @Override
    public void onFinish(ITestContext context) {
        if(extent != null) {
            extent.flush();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..." +
                "\nTEST STEPS"));
        ExtentTest test = extent.createTest(getTestClassName(result)
                                            + " | "
                                            + getTestMethodName(result));
        extentParallel.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
        String logText = "PASSED";
        Markup stylizedStatus = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        extentParallel.get().log(Status.PASS, stylizedStatus);
    }

    @Override
    public void onTestFailure(ITestResult result) {

        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();
        String screenshotPath = getScreenshotPath(getTestMethodName(result),
                getTestClassName(result),driver);

        try {
            extentParallel.get().fail("Screenshot taken: ",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (IOException e) {
            extentParallel.get().fail("Test failed, cannot attach screenshot.");
        }
        extentParallel.get().error(result.getThrowable());
        System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
        Markup stylizedStatus = MarkupHelper.createLabel("FAILED", ExtentColor.RED);
        extentParallel.get().log(Status.FAIL, stylizedStatus);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
        String logText = "SKIPPED";
        Markup stylizedStatus = MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
        extentParallel.get().log(Status.SKIP, stylizedStatus);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }


}
