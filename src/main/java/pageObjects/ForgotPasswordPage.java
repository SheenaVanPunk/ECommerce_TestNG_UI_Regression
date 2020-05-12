package pageObjects;

import classesUtilities.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends Page {
private By inputField = By.id("user_login");
private By resetPasswordButton = By.cssSelector("button[value = 'Reset password']");
private By errorMessage = By.cssSelector("ul.woocommerce-error li");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    public void setUsername(String username){
        type(inputField, username, "USERNAME FIELD");
    }

    public void sendPasswordReset(String username){
        setUsername(username);
        clickOnElement(resetPasswordButton, "RESET PASSWORD BUTTON");
    }

    public String getErrorMessage(){
        return getWebElementText(errorMessage, "ERROR MESSAGE");
    }

    public String getPageFromUrl(String url){
        return driver.getCurrentUrl().substring(url.length());

    }
}
