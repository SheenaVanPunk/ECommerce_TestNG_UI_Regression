package testClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utilities.Page;
import utilities.WindowManager;

public class BaseTest {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    private static String url = "http://automationpractice.com/";

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver(getChromeDriverOptions());
        wait = new WebDriverWait(driver, 8);

       Page page = new Page(driver, wait);
   }

        @BeforeMethod
        public void goToHomePage () {
            driver.get(url);
            driver.manage().window().maximize();
        }


        private ChromeOptions getChromeDriverOptions () {
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            options.addArguments("--silent");
            return options;
        }

        public WindowManager getWindowManager () {
            return new WindowManager(driver, wait);
        }

        @AfterClass
        public void tearDown () {
            driver.quit();
        }
    }