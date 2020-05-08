package testClasses;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.utils.FileUtil;
import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageObjects.HomePage;
import classesUtilities.WindowManager;
import testUtilities.BrowserFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class BaseTest {
    protected static WebDriver driver;
    protected SoftAssert soft = new SoftAssert();
    protected static HomePage hp;


//    public WebDriver getDriver() {
//        return driver;
//    }

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
        return new HomePage(driver);
    }

        @AfterMethod (alwaysRun = true)
        public void tearDown () {
            driver.manage().deleteAllCookies();
            driver.quit();

        }

        public WindowManager getWindowManager () {
            return new WindowManager(driver);
        }

        public String getScreenshotPath(String testCaseName, WebDriver driver) throws IOException {
            TakesScreenshot camera = (TakesScreenshot) driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            String screenshotPath = System.getProperty("user.home") + "\\TestReports\\" + testCaseName + ".png";
            File file = new File(screenshotPath);
            Files.move(screenshot, file);
            return screenshotPath;
        }



    }
