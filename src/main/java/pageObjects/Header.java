package pageObjects;

import org.openqa.selenium.By;
import utilities.Page;

public class Header extends Page {
    private By topDisclaimer = By.cssSelector("p.woocommerce-store-notice");

    public Header(){
        super(driver);
    }

    public boolean isDisclaimerDisplayed(){
        return isElementDisplayed(topDisclaimer, "HEADER DISCLAIMER");
    }
}
