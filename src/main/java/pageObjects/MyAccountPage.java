package pageObjects;

import classesUtilities.StepsLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import classesUtilities.Page;

public class MyAccountPage extends Page {
    private By registrationAndLoginSection = By.cssSelector("div.woocommerce");
    private By usernameField = By.id("reg_username");
    private By emailField = By.id("reg_email");
    private By passwordField = By.id("reg_password");
    private By registerButtonLoc = By.cssSelector("button[name='register']");
    private By errorMessage = By.cssSelector("ul.woocommerce-error li");
    private By passwordValidation = By.cssSelector("div.woocommerce-password-strength");
    private By passwordValidatorHint = By.cssSelector("small.woocommerce-password-hint");

    private By forgotPasswordLink = By.cssSelector("p.lost_password a");


    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnRegisterButton(){
        clickOnElement(registerButtonLoc, "REGISTER BUTTON", 3);
    }

    public void setPassword(String password){
        type(passwordField, password, "PASSWORD FIELD");
    }

    public WordPressPage enterUserDataAndClickAButton(String username, String email, String password) {
        if (driver.getTitle().contains("My Account")) {
            scrollUntilElement(getWebElement(registrationAndLoginSection, "REGISTRATION SECTION"));
        }

        if (driver.findElement(usernameField).isDisplayed()) {
            type(usernameField, username, "USERNAME FIELD");
            type(emailField, email, "EMAIL FIELD");
            setPassword(password);
            clickOnRegisterButton();
        } else {
            new StepsLogger().error("The page isn't scrolled to the registration section.");
        }
        return new WordPressPage(driver);
    }

    public String getPasswordStrengthAttribute(){
        return driver.findElement(passwordValidation).getAttribute("class").substring(30);
    }

    public boolean checkIfPasswordIsValidated(String passwordStrength){
        boolean isValidated = passwordStrength != "short" || passwordStrength != "bad" ? true : false;
        return isValidated;
    }

    public String getErrorMessage(){
        return getWebElementText(errorMessage, "ERROR MESSAGE");
    }

    public boolean isPasswordValidatorPresent() {
        return isElementDisplayed(passwordValidation, "PASSWORD VALIDATOR", 2);
    }

    public String isRegisterButtonEnabled() {
        var registerButton = getWebElement(registerButtonLoc, "REGISTER BUTTON");
        String isEnabled = registerButton.isEnabled() ? "enabled" : "disabled";
        return isEnabled;
    }

    public void clearPasswordField() {
        clearField(passwordField, "PASSWORD FIELD");
    }

    public ForgotPasswordPage clickOnForgotPassword(){
        scrollUntilElement(getWebElement(forgotPasswordLink, "LOST YOUR PASSWORD LINK"));
        clickOnElement(forgotPasswordLink, "LOST YOUR PASSWORD LINK");
        return new ForgotPasswordPage(driver);
    }



}
