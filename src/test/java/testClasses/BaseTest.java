package testClasses;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageObjects.HomePage;
import classesUtilities.WindowManager;
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

    @BeforeMethod(description = "Initializing driver, launching the browser, opening home page and creating its instance")
    @Parameters({"url", "browser"})
    public HomePage initDriverAndGoToHomePage(String url, String browser) {
        try {
            setDriver(browser);
        } catch (Exception e) {
            System.out.println("Error....." + Arrays.toString(e.getStackTrace()));
        }

        driver.get(url);
        hp = new HomePage(driver);
        driver.manage().window().maximize();
        return new HomePage(driver);
    }

        @AfterMethod
        public void tearDown () {
            driver.manage().deleteAllCookies();
            driver.quit();

        }

        //moving taking screenshots on failure feature to TestListener onTestFailure method
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

        private static ChromeOptions getChromeDriverOptions() {
            ChromeOptions options = new ChromeOptions();
            //options.addArguments("start-maximized");
            //options.addArguments("--window-size=1325x744");
            options.setHeadless(true);
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            return options;
        }

        public WindowManager getWindowManager () {
            return new WindowManager(driver);
        }

        private void setDriver (String browserType){
            switch (browserType) {
                case "chrome" -> driver = initChromeDriver();
                case "firefox" -> driver = initFirefoxDriver();
                default -> {
                    System.out.println("browser : " + browserType + " is invalid, Launching Chrome as browser of choice..");
                    driver = initChromeDriver();
                }
            }
        }

        private static WebDriver initChromeDriver () {
            System.out.println("Launching new instance of Google Chrome...");
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            System.setProperty("webdriver.chrome.silentOutput", "true");
            driver = new ChromeDriver(getChromeDriverOptions());
            return driver;
        }

        private static WebDriver initFirefoxDriver () {
            System.out.println("Launching new instance of Firefox browser..");
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            driver = new FirefoxDriver();
            return driver;
        }


    }
