package pageObjects;

import classesUtilities.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ForgotPasswordPage extends Page {
    private By inputField = By.id("user_login");
    private By resetPasswordButton = By.cssSelector("button[value = 'Reset password']");
    private By errorMessage = By.cssSelector("ul.woocommerce-error li");
    private By confirmationMessage = By.id("post-8");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    public void setUsername(String username) {
        type(inputField, username, "USERNAME FIELD");
    }

    public void sendPasswordReset(String username) {
        if (getPageFromUrlEndpoint().equals("lost-password")) {
            setUsername(username);
            clickOnElement(resetPasswordButton, "RESET PASSWORD BUTTON");
        }else{
            log.error("Currently not on reset password page.");
        }
    }

    public String getErrorMessage() {
        return getWebElementText(errorMessage, "ERROR MESSAGE");
    }

    private List<String> parseUrlToStrings() {
        return List.of(driver.getCurrentUrl().split("/"));
    }

    public String getPageFromUrlEndpoint() {
        return parseUrlToStrings().get(4);
    }

    public String getResetQueryStringFromEndpoint(){
        return parseUrlToStrings().get(5);
    }

    public String getConfirmationMessageText() {
        return getWebElementText(confirmationMessage, "CONFIRMATION MESSAGE");
    }


}
