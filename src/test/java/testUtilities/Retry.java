package testUtilities;

import com.aventstack.extentreports.ExtentTest;
import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


import java.io.File;
import java.io.IOException;

public class Retry implements IRetryAnalyzer {
//     private int count = 0;
//     private static final int maxTry = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
                iTestResult.setStatus(iTestResult.FAILURE);
                return true;
            }
        } else {
            iTestResult.setStatus(iTestResult.SUCCESS);
        }
        return false;
    }

}
