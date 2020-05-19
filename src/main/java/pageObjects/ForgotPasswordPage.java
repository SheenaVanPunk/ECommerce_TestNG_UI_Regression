package pageObjects;

import classesUtilities.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ForgotPasswordPage extends Page {
    private By inputField = By.id("user_login");
    private By resetPasswordButton = By.cssSelector("button[value = 'Reset password']");
    private By errorMessage = By.cssSelector("ul.woocommerce-error li");
    private By confirmationMessage = By.id("post-8");

    private final String passwordField_FORMAT = "input#password_%s";
    private By saveButton = By.cssSelector("button[value='Save']");
    private By errorMessageLine = By.id("primary");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    public void setUsername(String username) {
        type(inputField, username, "USERNAME FIELD");
    }

    public void sendPasswordReset(String email) {
        if (getPageFromUrlEndpoint().equals("lost-password")) {
            setUsername(email);
            clickOnElement(resetPasswordButton, "RESET PASSWORD BUTTON");
        } else {
            log.error("Currently not on reset password page.");
        }
    }

    public List<String> getErrorMessages() {
        scrollUntilElement(getWebElement(errorMessageLine, "ERROR MESSAGE LINE"));
        return driver.findElements(errorMessage).stream().map(WebElement::getText).collect(Collectors.toList());
    }








    public String getConfirmationMessageText() {
        return getWebElementText(confirmationMessage, "CONFIRMATION MESSAGE");
    }


    public void setNewPasswordInField(String password, int fieldNumber) {
        if(getQueryStringFromEndpoint().contains("?show-reset-form=true")) {
            scrollUntilElement(getWebElement(errorMessageLine, "ERROR MESSAGE LINE"));
            type(By.cssSelector(String.format(passwordField_FORMAT, String.valueOf(fieldNumber))), password, "PASSWORD FIELD " + fieldNumber);
        }else{
            log.error("Not on a Create new password page!");
        }
    }

    public MyAccountPage saveNewPassword() {
        clickOnElement(saveButton, "SAVE BUTTON");
        return new MyAccountPage(driver);
    }
}
