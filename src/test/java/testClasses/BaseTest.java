package testClasses;

import classesUtilities.WindowManager;
import com.testautomationguru.ocular.Ocular;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;
import pageObjects.HomePage;
import testUtilities.BrowserFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;


public class BaseTest {
    protected WebDriver driver;
    protected SoftAssert soft = new SoftAssert();
    protected static HomePage hp;


    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public void setUpOcular(){
        String pathToSSFolder =  System.getProperty("user.dir") + "\\resources\\ssComparison\\";
        Ocular.config()
                .snapshotPath(Paths.get(pathToSSFolder + "baseline\\"))
                .resultPath(Paths.get(pathToSSFolder + "actual\\"));
    }

    @Parameters({"url", "browser"})
    @BeforeMethod(alwaysRun = true, description = "Initializing driver, launching the browser, opening home page and creating its instance")

    public HomePage initDriverAndGoToHomePage(String url, String browser) {
        try {
            BrowserFactory factory = new BrowserFactory();
            driver = factory.getDriver(browser);
        } catch (Exception e) {
            System.out.println("Error....." + Arrays.toString(e.getStackTrace()));
        }

        driver.get(url);
        hp = new HomePage(driver);
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
        String screenshotFolderPath = "\\resources\\failedTestScreenshots\\" + LocalDate.now() + "\\" + testClassName + "\\";
        try {
            File file = new File(screenshotFolderPath);
            if (!file.exists())
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
