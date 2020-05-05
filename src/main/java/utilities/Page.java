package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    public Page(WebDriver driver) {
        Page.driver = driver;
        Page.wait = new WebDriverWait(driver, 10);
    }

    protected WebElement getWebElement(By locator, String elementName) {
        WebElement element = null;
        try {
            waitForThePresenceOfElementInDom(locator);
            element = driver.findElement(locator);
        } catch (StaleElementReferenceException e) {
            new StepsLogger().error("Element " + elementName + "cannot be located on the page.");
            e.printStackTrace();
        }
        return element;
    }


    public String getWebElementText(By locator, String elementName) {
        waitForThePresenceOfElementInDom(locator);
        String e = getWebElement(locator, elementName).getText();
        new StepsLogger().step("Get text for WebElement " + elementName);
        new StepsLogger().info("Element's text: " + "\"" + e + "\"");
        return e;
    }

    public String getPageTitle() {
        String pt = driver.getTitle();
        new StepsLogger().info("Page title is " + "\"" + pt + "\"");
        return pt;
    }

    public void clickOnElement(By locator, String elementName) {
        try {
            waitForElementClickability(locator);
            getWebElement(locator, elementName).click();
            new StepsLogger().step("Click on " + elementName);
        } catch (StaleElementReferenceException e) {
            getWebElement(locator, elementName).click();
        }
    }

    public void clearField(By locator, String elementName) {
        waitForElementClickability(locator);
        getWebElement(locator, elementName).click();
        new StepsLogger().step("Clear the field " + elementName);
    }

    public void sendText(By locator, String elementName, String text) {
        waitForElementVisibility(locator);
        getWebElement(locator, elementName).sendKeys(text);
        new StepsLogger().step("Send text " + "\"" + text + "\"" + " to " + elementName);
    }

    public boolean isElementDisplayed(By locator, String elementName) {
        boolean displayed = getWebElement(locator, elementName).isDisplayed();
        waitForElementVisibility(locator);
        if (displayed) {
            new StepsLogger().info(elementName + " is displayed");
        } else {
            new StepsLogger().info(elementName + " is not displayed");
        }
        return displayed;
    }

    public Select createSelectElement(By locator) {
        return new Select(driver.findElement(locator));
    }

    public void selectByText(By locator, String text, String elementName) {
        waitForElementClickability(locator);
        createSelectElement(locator).selectByVisibleText(text);
        new StepsLogger().step("Select " + text + " from " + elementName + " dropdown");
    }


    public void waitForAllTabsToLoad() {
        int complete = driver.getWindowHandles().size();
        wait.until(ExpectedConditions.numberOfWindowsToBe(complete));
    }

    public void waitForElementVisibility(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementClickability(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForThePresenceOfElementInDom(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForInvisibilityOfElement(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void scrollUntilElement(Object object) {
        String script = "arguments[0].scrollIntoView();";

        ((JavascriptExecutor) driver).executeScript(script, object);

    }

    public String getTabHandle() {
        return driver.getWindowHandle();
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

}
