package pageObjects;

import classesUtilities.Page;
import classesUtilities.StepsLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public class MyAccountPage extends Page {
    private By registrationAndLoginSection = By.cssSelector("div.woocommerce");
    private By usernameFieldRegistration = By.id("reg_username");
    private By emailFieldRegistration = By.id("reg_email");
    private By passwordFieldRegistration = By.id("reg_password");
    private String buttonLoc = "button[name='%s']";
    private By errorMessage = By.cssSelector("ul.woocommerce-error li");
    private By validationMessage = By.cssSelector("div[role='alert']");
    private By passwordValidation = By.cssSelector("div.woocommerce-password-strength");
    private By passwordValidatorHint = By.cssSelector("small.woocommerce-password-hint");
    private By passwordFieldLogin = By.id("password");
    private By usernameFieldLogin = By.id("username");
    private By usernameOnDashboard = By.cssSelector("strong");
    private By dashboardLinks = By.cssSelector("nav a");
    private By errorMessageFrame = By.id("primary");

    private By forgotPasswordLink = By.cssSelector("p.lost_password a");


    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnButton(String type){
        // type: register or login
        clickOnElement(By.cssSelector(String.format(buttonLoc, type)), "REGISTER BUTTON", 3);
    }

    public void setPasswordForRegistration(String password){
        type(passwordFieldRegistration, password, "PASSWORD FIELD");
    }

    public void setLoginUserName(String username){
        type(usernameFieldLogin, username, "LOGIN USERNAME FIELD");
    }

    public void setLoginPassword(String password){
        type(passwordFieldLogin, password, "LOGIN PASSWORD FIELD");
    }

    public WordPressPage enterUserDataAndClickAButton(String username, String email, String password) {
        if (driver.getTitle().contains("My Account")) {
            scrollUntilElement(getWebElement(registrationAndLoginSection, "REGISTRATION SECTION"));
        }

        if (driver.findElement(usernameFieldRegistration).isDisplayed()) {
            type(usernameFieldRegistration, username, "USERNAME FIELD");
            type(emailFieldRegistration, email, "EMAIL FIELD");
            setPasswordForRegistration(password);
            clickOnButton("register");
        } else {
            new StepsLogger().error("The page isn't scrolled to the registration section.");
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

    public boolean checkIfPasswordIsValidated(String passwordStrength){
        return passwordStrength != "short" || passwordStrength != "bad" ? true : false;
    }

    public String getErrorMessage(){
        scrollUntilElement(getWebElement(errorMessageFrame, "ERROR MESSAGE"));
        return getWebElementText(errorMessage, "ERROR MESSAGE");
    }

    public String getValidationMessage(){
        return getWebElementText(validationMessage, "VALIDATION MESSAGE");
    }

    public boolean isPasswordValidatorPresent() {
        if(driver.findElement(passwordValidation).getText().contains("")) {
            return isElementDisplayed(passwordValidation, "PASSWORD VALIDATOR", 2);
        }else{
            System.out.println("The password field is not empty.");
        }
        return true;
    }

    public String isButtonEnabled(String type) {
        var registerButton = getWebElement(By.cssSelector(String.format(buttonLoc, type)), "REGISTER BUTTON");
        return registerButton.isEnabled() ? "enabled" : "disabled";
    }

    public void clearPasswordField() {
        clearField(passwordFieldRegistration, "PASSWORD FIELD");
    }

    public ForgotPasswordPage clickOnForgotPassword(){
        scrollUntilElement(getWebElement(usernameFieldLogin, "LOST YOUR PASSWORD LINK"));
        clickOnElement(forgotPasswordLink, "LOST YOUR PASSWORD LINK");
        return new ForgotPasswordPage(driver);
    }

    public String getUsernameAfterLogin(){
        return getWebElementText(usernameOnDashboard, "USERNAME");
    }

    private List<WebElement> getDashboardLinks(){
        return driver.findElements(dashboardLinks);
    }

    public int countDashboardLinks(){
       return getDashboardLinks().size();
    }

    public List<String> getDashboardLinksNames(){

        for (WebElement link : getDashboardLinks()){
            return Arrays.asList(link.getText());
        }
        return null;
    }



}
