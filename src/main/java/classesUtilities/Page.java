package classesUtilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



public class Page {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    private static final StepsLogger log = new StepsLogger();

    public Page(WebDriver driver) {
        Page.driver = driver;
        Page.wait = new WebDriverWait(driver, 15);
    }

    protected WebElement getWebElement(By locator, String elementName) {
        WebElement element = null;
        try {
            waitForThePresenceOfElementInDom(locator);
            element = driver.findElement(locator);
        } catch (StaleElementReferenceException e) {
            log.error("Element " + elementName + "cannot be located on the page.");
            e.printStackTrace();
        }
        return element;
    }


    public String getWebElementText(By locator, String elementName) {
        waitForThePresenceOfElementInDom(locator);
        String e = getWebElement(locator, elementName).getText();
        log.step("Get text for WebElement " + elementName);
        log.info("Element's text: " + "\"" + e + "\"");
        return e;
    }

    public String getPageTitle() {
        String pt = driver.getTitle();
        log.info("Page title is " + "\"" + pt + "\"");
        return pt;
    }

    public void clickOnElement(By locator, String elementName) {
            waitForElementClickability(locator);
            getWebElement(locator, elementName).click();
            log.step("Click on " + "\"" + elementName + "\"");
    }

    public void clickOnElement(WebElement element, String elementName) {
        try {
            waitForElementClickability(element);
            element.click();
            log.step("Click on " + "\"" + elementName + "\"");
        } catch (StaleElementReferenceException e) {
            log.error("Element " + elementName + "cannot be located on the page.");
            element.click();
            e.getMessage();
        }catch(TimeoutException e){
            element.click();
        }
    }

    public void clearField(By locator, String elementName) {
        waitForElementClickability(locator);
        getWebElement(locator, elementName).click();
        log.step("Clear the field " + elementName);
    }

    public void type(By locator, String elementName, String text) {
        waitForElementVisibility(driver.findElement(locator));
        getWebElement(locator, elementName).sendKeys(text);
        log.step("Send text " + "\"" + text + "\"" + " to " + elementName);
    }

    public boolean isElementDisplayed(By locator, String elementName) {
        boolean displayed = getWebElement(locator, elementName).isDisplayed();
        waitForElementVisibility(driver.findElement(locator));
        if (displayed) {
            log.info(elementName + " is displayed");
        } else {
            log.info(elementName + " is not displayed");
        }
        return displayed;
    }

    public Select createSelectElement(By locator) {
        return new Select(driver.findElement(locator));
    }

    public void selectByText(By locator, String text, String elementName) {
        waitForElementClickability(locator);
        createSelectElement(locator).selectByVisibleText(text);
        log.step("Select " + text + " from " + elementName + " dropdown");
    }




    public void waitForAllTabsToLoad() {
        int complete = driver.getWindowHandles().size();
        wait.until(ExpectedConditions.numberOfWindowsToBe(complete));
    }

    public void waitForElementVisibility(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch(TimeoutException e){
            log.error("Timeout - the wait time expired and the element is still not visible.");
        }
    }

    public void waitForElementClickability(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        }catch (TimeoutException e){
            log.error("Timeout - the wait time expired and the element is still not clickable.");
            e.getMessage();
        }
    }
    public void waitForElementClickability(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch(TimeoutException e){
            log.error("Timeout - the wait time expired and the element is still not clickable.");
            e.getMessage();
        }
    }

    public void waitForThePresenceOfElementInDom(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        }catch(TimeoutException e){
            log.error("Timeout - the wait time expired and the element is still not present in DOM.");
            e.getMessage();
        }
    }

    public void waitForInvisibilityOfElement(By locator) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }catch(TimeoutException e){
            log.error("Timeout - the wait time expired and the element is still not invisible.");
            e.getMessage();
        }
    }

    public void scrollUntilElement(WebElement element) {
        String script = "arguments[0].scrollIntoView();";
        log.info("Scrolling to element..." );
        waitForElementVisibility(element);
        ((JavascriptExecutor) driver).executeScript(script, element);

    }

    public String getTabHandle() {
        return driver.getWindowHandle();
    }



}
