package pageObjects;

import classesUtilities.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardPage extends Page {

    private By usernameOnDashboard = By.cssSelector("strong");
    private By dashboardLinks = By.cssSelector("nav a");
    private By dashboardSection = By.id("primary");
    private By logOutLinkInText = By.linkText("Log out");
    private By homePageLinkInBanner = By.cssSelector("span[property='name']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public String getUsernameAfterLogin(){
        return getWebElementText(usernameOnDashboard, "USERNAME DISPLAYED");
    }

    private List<WebElement> getDashboardLinks(){
        return driver.findElements(dashboardLinks);
    }

    public int countDashboardLinks(){
        return getDashboardLinks().size();
    }

    public List<String> getDashboardLinksNames(){
        scrollUntilElement(getDashboardLinks().get(0));
        return getDashboardLinks()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public MyAccountPage logOut() {
        scrollUntilElement(getWebElement(dashboardSection, "DASHBOARD SECTION"));
        clickOnElement(getDashboardLinks().get(getDashboardLinks().size()-1), "LOG OUT LINK");
        return new MyAccountPage(driver);
    }

    public MyAccountPage logOutWithLinkInsideWelcomeText() {
        scrollUntilElement(driver.findElement(homePageLinkInBanner));
        clickOnElement(logOutLinkInText, "LOG OUT LINK IN WELCOME TEXT");
        return new MyAccountPage(driver);
    }
}
