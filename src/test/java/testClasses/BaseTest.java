package testClasses;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageObjects.HomePage;
import classesUtilities.WindowManager;
import testUtilities.BrowserFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import java.util.Arrays;


public class BaseTest {
    protected static WebDriver driver;
    protected SoftAssert soft = new SoftAssert();
    protected static HomePage hp;

    public WebDriver getDriver() {
        return driver;
    }


    @BeforeMethod(alwaysRun = true, description = "Initializing driver, launching the browser, opening home page and creating its instance")
    @Parameters({"url", "browser"})
    public HomePage initDriverAndGoToHomePage(String url, String browser) {
        try {
            BrowserFactory.getDriver(browser);
        } catch (Exception e) {
            System.out.println("Error....." + Arrays.toString(e.getStackTrace()));
        }

        driver.get(url);
        hp = new HomePage(driver);
        driver.manage().window().maximize();
        return hp;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.manage().deleteAllCookies();
        driver.quit();

    }

    public WindowManager getWindowManager() {
        return new WindowManager(driver);
    }

    public String getScreenshotPath(String testCaseName, String testClassName, WebDriver driver) {
        String screenshotFolderPath = "\\resources\\failedTestScreenshots\\"+ LocalDate.now() +"\\" + testClassName + "\\";
        try {
            File file = new File(screenshotFolderPath);
            if(!file.exists())
            System.out.println("New folder for storing screenshots created " + file);
            file.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TakesScreenshot camera = (TakesScreenshot) driver;
        File screenshot = camera.getScreenshotAs(OutputType.FILE);
        String screenshotPath = System.getProperty("user.dir") + screenshotFolderPath + testCaseName + ".png";
        File file = new File(screenshotPath);
        try {
            FileUtils.copyFile(screenshot, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }
}
