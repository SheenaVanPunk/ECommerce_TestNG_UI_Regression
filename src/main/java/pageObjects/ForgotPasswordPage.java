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

    private By newPasswordField = By.id("password_1");
    private By repeatPasswordField = By.id("password_2");
    private By saveButton = By.cssSelector("button[value='Save']");

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


    public void setNewPassword(String password) {
        type(newPasswordField, password, "NEW PASSWORD FIELD");
    }

    public void repeatNewPassword(String password) {
        type(repeatPasswordField, password, "REPEAT PASSWORD FIELD");
    }

    public MyAccountPage saveNewPassword() {
        clickOnElement(saveButton, "SAVE BUTTON");
        return new MyAccountPage(driver);
    }
}
