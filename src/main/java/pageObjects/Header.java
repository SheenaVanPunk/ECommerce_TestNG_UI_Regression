package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import classesUtilities.Page;

public class Header extends Page {
    private By topDisclaimer = By.cssSelector("p.woocommerce-store-notice");

    public Header(WebDriver driver){
        super(driver);
    }

    public boolean isDisclaimerDisplayed(){
        return isElementDisplayed(topDisclaimer, "HEADER DISCLAIMER");
    }
}
