package utilities;

import org.testng.*;
import org.testng.annotations.Listeners;
import org.testng.log4testng.Logger;


public class TestListener extends TestListenerAdapter {
StepsLogger log = new StepsLogger();

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Starting test: " + iTestResult.getInstanceName() +
                            "\n"+ iTestResult.getName() +
                            "\n" +
                            "\nTEST STEPS: ");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
       log.passed(iTestResult.getInstanceName() +" : " + iTestResult.getName());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.failed(iTestResult.getInstanceName() + " : " + iTestResult.getName());
        System.out.println(iTestResult.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("TEST SKIPPED " + iTestResult.getInstanceName() + " : " + iTestResult.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("Failed test methods:" + iTestContext.getFailedTests());
        System.out.println("Execution started at: " + iTestContext.getStartDate() +
                            "\nExecution ended at: " + iTestContext.getEndDate());

        double lenghtOfExecution = (iTestContext.getEndDate().getTime()
                                    - iTestContext.getStartDate().getTime()) / 1000;
        System.out.println("Execution lasted for: " + lenghtOfExecution  + " seconds.");
    }

}
