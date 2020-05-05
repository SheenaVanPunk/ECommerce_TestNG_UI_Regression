package testClasses;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageObjects.HomePage;
import utilities.LoggingFileSetup;
import utilities.TestListener;
import utilities.WindowManager;

import java.io.File;
import java.io.IOException;

public class BaseTest extends LoggingFileSetup {
    protected static WebDriver driver;
    private static String url = "http://shop.demoqa.com/";
    protected static HomePage hp;

    @BeforeClass
    public void init() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        driver = new ChromeDriver(getChromeDriverOptions());

    }

    @BeforeMethod
    public void goToHomePage() {
//        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
//        System.setProperty("webdriver.chrome.silentOutput", "true");
//        driver = new ChromeDriver(getChromeDriverOptions());
        driver.get(url);
        hp = new HomePage(driver);

    }


    @AfterMethod
    public void recordFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            var camera = (TakesScreenshot) driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            try {
                Files.move(screenshot, new File("resources/failedTestScreenshots/" + result.getName() + ".png"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        driver.manage().deleteAllCookies();
//        driver.quit();
    }

    private ChromeOptions getChromeDriverOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        //options.addArguments("--window-size=1325x744");
       //options.setHeadless(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }

    public WindowManager getWindowManager() {
        return new WindowManager(driver);
    }

    @AfterClass
    public void tearDown() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }


}