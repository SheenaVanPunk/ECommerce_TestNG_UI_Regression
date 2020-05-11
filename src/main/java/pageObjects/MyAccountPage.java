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
    private By registerButton = By.cssSelector("button[name='register']");
    private By errorMessage = By.cssSelector("ul.woocommerce-error li");
    private By passwordStrength = By.cssSelector("div.woocommerce-password-strength");


    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public void setUsername(String username){
        type(usernameField, username, "USERNAME FIELD");
    }

    public void setEmail(String email){
        type(emailField, email, "EMAIL FIELD");
    }

    public void setPassword(String password){
        type(passwordField, password, "PASSWORD FIELD");
    }

    public void clickOnRegisterButton(){
        clickOnElement(registerButton, "REGISTER BUTTON");

    }

    public WordPressPage enterUserDataAndClickAButton(String username, String email, String password) {
        if (driver.getTitle().contains("My Account")) {
            scrollUntilElement(getWebElement(registrationAndLoginSection, "REGISTRATION SECTION"));
        }

        if (driver.findElement(usernameField).isDisplayed()) {
            type(usernameField, "USERNAME FIELD", username);
            type(emailField, "EMAIL FIELD", email);
            type(passwordField, "PASSWORD FIELD", password);
            clickOnRegisterButton();
           // checkIfPasswordIsValidated();
        } else {
            new StepsLogger().error("The page isn't scrolled to the registration section.");
        }
        return new WordPressPage(driver);
    }

    public String getPasswordStrengthAttribute(){
        return driver.findElement(passwordStrength).getAttribute("class").substring(30);
    }

    public boolean checkIfPasswordIsValidated(String passwordStrength){
        boolean isValidated = passwordStrength != "short" || passwordStrength != "bad" ? true : false;
        return isValidated;
    }

    public String getErrorMessage(){
        return getWebElementText(errorMessage, "ERROR MESSAGE");
    }

//    public boolean isCorrectErrorMessageDisplayed(){
//
//    }
    /*

----------------------------------------
Error: An account is already registered with that username. Please choose another.
Error: Please provide a valid email address.
Error: Please enter a valid account username.
Error: Please enter an account password.


     */


}
