package testClasses;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageObjects.HomePage;
import classesUtilities.WindowManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class BaseTest {
    protected static WebDriver driver;
    protected SoftAssert soft = new SoftAssert();
    protected static HomePage hp;
    private static final String chromeDriverPath = "resources/chromedriver.exe";
    private static final String firefoxDriverPath = "resources/geckodriver.exe";

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeClass (description = "Initializing driver and launching the browser")
    @Parameters("browser")
    public void initializeDriver(String browser) {
        try {
            setDriver(browser);

        } catch (Exception e) {
            System.out.println("Error....." + Arrays.toString(e.getStackTrace()));
        }
    }

    @BeforeMethod (description = "Opening home page and creating its instance")
    @Parameters("url")
    public void goToHomePage(String url) {
        driver.get(url);
        hp = new HomePage(driver);
        driver.manage().window().maximize();
    }


    @AfterMethod
//    public void recordFailure(ITestResult result) {
//        if (ITestResult.FAILURE == result.getStatus()) {
//            var camera = (TakesScreenshot) driver;
//            File screenshot = camera.getScreenshotAs(OutputType.FILE);
//            try {
//                Files.move(screenshot, new File("resources/failedTestScreenshots/" + result.getName() + ".png"));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();

    }

    private ChromeOptions getChromeDriverOptions() {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("start-maximized");
        //options.addArguments("--window-size=1325x744");
        //options.setHeadless(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }

    public WindowManager getWindowManager() {
        return new WindowManager(driver);
    }

    @AfterClass (description = "Quitting the browser instance.")
    public void tearDown() {

        driver.quit();
    }

    private void setDriver(String browserType) {
        switch (browserType) {
            case "chrome" -> driver = initChromeDriver();
            case "firefox" -> driver = initFirefoxDriver();
            default -> {
                System.out.println("browser : " + browserType + " is invalid, Launching Chrome as browser of choice..");
                driver = initChromeDriver();
            }
        }
    }

    private static WebDriver initChromeDriver() {
        System.out.println("Launching Google Chrome with new profile..");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        System.setProperty("webdriver.chrome.silentOutput", "true");
        driver = new ChromeDriver();
        return driver;
    }

    private static WebDriver initFirefoxDriver() {
        System.out.println("Launching Firefox browser..");
        System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
        driver = new FirefoxDriver();
        return driver;
    }


}