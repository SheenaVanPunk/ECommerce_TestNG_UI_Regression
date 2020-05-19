package pageObjects;

import classesUtilities.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

public class MyAccountPage extends Page {

    private static final String usernameField_FORMAT = "%susername";
    private static final String passwordField_FORMAT = "%spassword";
    private By emailFieldRegistration = By.id("reg_email");
    private String button_FORMAT = "button[name='%s']";
    private By errorMessage = By.cssSelector("ul.woocommerce-error li");
    private By validationMessage = By.cssSelector("div[role='alert']");
    private By passwordValidation = By.cssSelector("div.woocommerce-password-strength");
    private By passwordValidatorHint = By.cssSelector("small.woocommerce-password-hint");
    private By errorMessageFrame = By.id("primary");
    private By forgotPasswordLink = By.cssSelector("p.lost_password a");
    private By rememberMeCheckbox = By.id("rememberme");

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnButton(String buttonType){
       clickOnElement(By.cssSelector(String.format(button_FORMAT, buttonType)), buttonType.toUpperCase()+" BUTTON", 3);
    }

    //log in user
    public DashboardPage enterUserDataAndClickLoginButton(String email, String password) {
        if (driver.getTitle().contains("My Account")) {
            scrollUntilElement(getWebElement(errorMessageFrame, "LOGIN SECTION"));
        }

        By usernameField = By.id(String.format(usernameField_FORMAT, ""));
        By passwordField = By.id(String.format(passwordField_FORMAT, ""));
        if (driver.findElement(usernameField).isDisplayed()) {
            type(usernameField, email, "EMAIL FIELD");
            type(passwordField, password, "PASSWORD FIELD");
            clickOnButton("login");
        }
        return new DashboardPage(driver);
    }

    //register user
    public WordPressPage enterUserDataAndClickLoginButton(String username, String email, String password) {
        if (driver.getTitle().contains("My Account")) {
            scrollUntilElement(getWebElement(errorMessageFrame, "REGISTRATION SECTION"));
        }
        String authType = "reg_";
        By usernameField = By.id(String.format(usernameField_FORMAT, authType));
        By passwordField = By.id(String.format(passwordField_FORMAT, authType));
        if (driver.findElement(usernameField).isDisplayed()) {
            type(usernameField, username, "USERNAME FIELD");
            type(emailFieldRegistration, email, "EMAIL FIELD");
            type(passwordField, password, "PASSWORD FIELD");
            clickOnButton("register");
        }
        return new WordPressPage(driver);
    }

    public String getPasswordStrengthAttribute(){
        if(driver.findElement(passwordValidation).isDisplayed()){
            return driver.findElement(passwordValidation).getAttribute("class").substring(30);}
        else{
            throw new StaleElementReferenceException("The password validator is not displayed.");
        }
    }
//    this method is not in use at the moment
//    public boolean checkIfPasswordIsValidated(String passwordStrength){
//        return passwordStrength != "short" || passwordStrength != "bad" ? true : false;
//    }

    public String getErrorMessage(){
        scrollUntilElement(getWebElement(errorMessageFrame, "ERROR MESSAGE"));
        return getWebElementText(errorMessage, "ERROR MESSAGE");
    }

    public String getValidationMessage(){
        return getWebElementText(validationMessage, "VALIDATION MESSAGE");
    }

    public boolean isPasswordValidatorPresent() {
        getWebElement(passwordValidation, "PASSWORD VALIDATOR");
        return isElementDisplayed(passwordValidation, "PASSWORD VALIDATOR", 2);
    }

    public String isButtonEnabled(String type) {
        var button = getWebElement(By.cssSelector(String.format(button_FORMAT, type)), type.toUpperCase() + " BUTTON");
        return button.isEnabled() ? "enabled" : "disabled";
    }

    public void clearPasswordField() {
        clearField(By.id(String.format(passwordField_FORMAT,"reg_")), "PASSWORD FIELD");
    }

    public ForgotPasswordPage clickOnForgotPassword(){
        By usernameField = By.id(String.format(usernameField_FORMAT, ""));
        scrollUntilElement(getWebElement(usernameField, "LOST YOUR PASSWORD LINK"));
        clickOnElement(forgotPasswordLink, "LOST YOUR PASSWORD LINK");
        return new ForgotPasswordPage(driver);
    }


    public void setPasswordForRegistration(String password) {
        type(By.id(String.format(passwordField_FORMAT, "reg_")), password, "PASSWORD FIELD");
    }

    public boolean clickOnCheckbox(){
        clickOnElement(rememberMeCheckbox, "REMEMBER ME CHECKBOX");
        return driver.findElement(rememberMeCheckbox).isSelected();
    }


    public void enterEmailAndPassword(String email, String password) {
        if (driver.getTitle().contains("My Account")) {
            scrollUntilElement(getWebElement(errorMessageFrame, "LOGIN SECTION"));
        }

        By usernameField = By.id(String.format(usernameField_FORMAT, ""));
        By passwordField = By.id(String.format(passwordField_FORMAT, ""));
        if (driver.findElement(usernameField).isDisplayed()) {
            type(usernameField, email, "EMAIL FIELD");
            type(passwordField, password, "PASSWORD FIELD");
        }
    }

    public String getUsernameFromField() {
        scrollUntilElement(driver.findElement(errorMessageFrame));
        return getWebElement(By.id(String.format(usernameField_FORMAT,"")), "USERNAME FIELD LOGIN")
                .getAttribute("value");
    }

    public DashboardPage clickOnLoginButton() {
        clickOnElement(By.cssSelector(String.format(button_FORMAT,"login")), "LOGIN BUTTON");
        return new DashboardPage(driver);
    }

    public void clearUsernameLoginField(){
        clearField(By.id(String.format(usernameField_FORMAT,"")), "USERNAME FIELD FOR LOGIN");
    }


    public boolean onMyAccountPage() {
        return getPageFromUrlEndpoint().contains("my-account");
    }
}
