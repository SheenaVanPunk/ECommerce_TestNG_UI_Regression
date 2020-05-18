package testClasses;

import classesUtilities.nadaEmailApiClasses.NadaEmailService;
import org.testng.annotations.Test;
import pageObjects.ForgotPasswordPage;
import pageObjects.MyAccountPage;
import pageObjects.WordPressPage;
import testUtilities.CsvParser;

import java.io.IOException;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static testUtilities.TestListener.extentParallel;

public class MyAccountTest extends BaseTest {

   private static NadaEmailService nada = new NadaEmailService();
    private String email = nada.getEmailId();
    private String username = nada.generateUsername();
    private String password = nada.generateUserPassword();

    private static final String RESET_EMAIL_SUBJECT = "Password Reset Request for ToolsQA Demo Site";
    private static final String REGISTRATION_EMAIL_SUBJECT = "Your ToolsQA Demo Site account has been created!";
    private static final String RESET_EMAIL_TITLE = "Password Reset Request";
    private static final String REGISTRATION_EMAIL_TITLE = "Welcome to ToolsQA Demo Site";
    private static final String PASSWORD_RESET_CONFIRM_QUERY = "?password-reset=true";


    @Test(groups = "registration", enabled=false)
    public void testSuccessfulRegistration(){
        MyAccountPage ap = new MyAccountPage(driver);
        extentParallel.get().info("Test Case no. 1: Valid user data");

        WordPressPage wp = ap.enterUserDataAndClickAButton(username, email, password);
        extentParallel.get().info("Submitted new user credentials: "+ username + " " + email + " " + password);

        String emailContent = nada.getNewMessageWithSubject(REGISTRATION_EMAIL_SUBJECT, email).getEmailContent();
        extentParallel.get().info("Fetched the validation email with subject " + "\"" + REGISTRATION_EMAIL_SUBJECT + "\"");

        soft.assertEquals(wp.getActualPageTitle(), wp.getExpectedPageTitle(), "Registration process has not end" +
                "up on the WordPress page.");
        soft.assertTrue(emailContent.contains(REGISTRATION_EMAIL_TITLE), "Email doesn't contain the expected title " + "\"" + REGISTRATION_EMAIL_TITLE + "\"");
        soft.assertTrue(emailContent.contains(username), "Email doesn't contain the correct username " + "\"" + username + "\"");

        soft.assertAll();
    }


    @Test(dataProvider = "csvParser", dataProviderClass = CsvParser.class, groups = "registration", enabled = false)
    public void testRegistrationErrorMessages(Map<String, String> testData) {
        String testCaseNo = testData.get("testCaseNo");
        String username = testData.get("username");
        String email = testData.get("email");
        String password = testData.get("password");
        String description = testData.get("description");
        String expectedMessage = testData.get("message");
        extentParallel.get().info("Test Case no. " + testCaseNo + ": " + description);

        MyAccountPage ap = new MyAccountPage(driver);
        ap.enterUserDataAndClickAButton(username, email, password);
        extentParallel.get().info("Submitted user credentials: "+ "\"" + username + "\", " + "\""  + email + "\", " + "\"" + password + "\"");
        String actualErrorMessage = ap.getErrorMessage();
        extentParallel.get().info("Fetched the error message: " + actualErrorMessage);

        assertEquals(actualErrorMessage, expectedMessage, "The actual error message doesn't match expected.");
    }


    @Test(groups = "registration", dataProvider = "csvParser", dataProviderClass = CsvParser.class, enabled = false)
    public void testPasswordStrengthValidation(Map<String, String> testData) {
        String testCaseNo = testData.get("testCaseNo");
        String description = testData.get("description");
        String password = testData.get("password");
        String expectedPasswordStrength = testData.get("validation");
        String expectedButtonState = testData.get("buttonState");
        String buttonType = "register";

        extentParallel.get().info("Test Case no. " + testCaseNo + ": " + description);
        MyAccountPage ap = new MyAccountPage(driver);
        ap.setPasswordForRegistration(password); //type a password
        extentParallel.get().info("Entered password: " + password);
        String actualPasswordStrength = ap.getPasswordStrengthAttribute(); //get password strength
        extentParallel.get().info("Password evaluated as: " + actualPasswordStrength.toUpperCase());
        String actualRegisterButtonState = ap.isButtonEnabled(buttonType); // get button state
        extentParallel.get().info("Register button is: " + actualRegisterButtonState.toUpperCase());
        ap.clearPasswordField(); //clear field
        extentParallel.get().info("Cleared password field.");
        boolean actualPasswordValidatorAbsence = ap.isPasswordValidatorPresent();
        extentParallel.get().info("Password validator is present - " + String.valueOf(actualPasswordValidatorAbsence).toUpperCase());

        soft.assertEquals(actualPasswordStrength, expectedPasswordStrength, "Displayed validation level does not match the expected."); //verify if the password validation level is appropriate
        soft.assertEquals(actualRegisterButtonState, expectedButtonState, "The register button state was not found in the expected state."); //verify if register button is enabled
        soft.assertFalse(actualPasswordValidatorAbsence, "The password validator is still visible."); // verify that the password validator ribbon in not displayed anymore

        soft.assertAll();
    }


    @Test(groups = "password reset", dependsOnMethods = "testSuccessfulRegistration", enabled=false)
    public void testSuccessfulResetEmailSent() throws IOException {
       String expectedValidationMessage = "Password reset email has been sent.";
       String expectedQueryString = "?reset-link-sent=true";

        MyAccountPage ap = new MyAccountPage(driver);
        ForgotPasswordPage fp = ap.clickOnForgotPassword();
        extentParallel.get().info("Scrolled to the login section and clicked on LOST YOUR PASSWORD link");
        fp.sendPasswordReset(email);
        extentParallel.get().info("Entered the registered email address " + email + " and sent a password reset e-mail");

        String resetLink = nada.getNewLinkFromResetEmail(RESET_EMAIL_SUBJECT, email);
        String emailContent = nada.getNewMessageWithSubject(RESET_EMAIL_SUBJECT, email).getEmailContent();
        extentParallel.get().info("Fetched the password reset email.");

        soft.assertTrue(fp.getConfirmationMessageText().contains(expectedValidationMessage), "The validation message is not displayed.");
        soft.assertTrue(fp.getResetQueryStringFromEndpoint().contains(expectedQueryString), "The query string is not added to URL.");
        soft.assertTrue(emailContent.contains(RESET_EMAIL_TITLE), "The email title is not " + RESET_EMAIL_TITLE);
        soft.assertFalse(resetLink.isEmpty(), "The URL could not be found in password reset e-mail.");

        soft.assertAll();
    }


    @Test(groups = "password reset", dataProvider = "csvParser", dataProviderClass = CsvParser.class, enabled = false)
    public void testUnsuccessfulResetEmailSent(Map<String, String> testData) {
        String invalidUserID = testData.get("invalidEmailOrUsername");
        String expectedErrorMessage = testData.get("message");

        MyAccountPage ap = new MyAccountPage(driver);
        ForgotPasswordPage fp = ap.clickOnForgotPassword();
        extentParallel.get().info("Clicked on the LOST YOUR PASSWORD link");
        fp.sendPasswordReset(invalidUserID);
        extentParallel.get().info("Entered invalid username/e-mail: " + invalidUserID);
        String actualErrorMessage = fp.getErrorMessage();

        assertEquals(actualErrorMessage, expectedErrorMessage, "A correct error message is not displayed.");
        extentParallel.get().info("Displayed error message is correct: " + actualErrorMessage);
    }


    @Test(groups = "password reset", dependsOnMethods = "testSendPasswordReset", enabled = false)
    public void testSuccessfulPasswordResetWithResetLink() throws IOException {
        String newPassword = nada.generateUserPassword();
        String expectedValidationMessage = "Your password has been reset successfully.";
        String expectedPage = "my-account";

        String resetLinkFromValidationEmail = nada.getNewLinkFromResetEmail(RESET_EMAIL_SUBJECT, email);
        extentParallel.get().info("Fetched the reset link from received e-mail: " + resetLinkFromValidationEmail);
        getWindowManager().goToUrl(resetLinkFromValidationEmail); //click on a reset link
        extentParallel.get().info("Opened the reset link in a browser.");
        ForgotPasswordPage fp = new ForgotPasswordPage(driver);
        fp.setNewPassword(newPassword);
        extentParallel.get().info("Entered new password for the 1st time: " + newPassword);
        fp.repeatNewPassword(newPassword);
        extentParallel.get().info("Repeated new password.");
        MyAccountPage ap = fp.saveNewPassword();
        extentParallel.get().info("Saved new password.");

        soft.assertTrue(ap.getValidationMessage().contains(expectedValidationMessage), "The expected validation message is not displayed.");
        soft.assertTrue(fp.getResetQueryStringFromEndpoint().contains(PASSWORD_RESET_CONFIRM_QUERY), "The password reset confirmation query string cannot be located in URL.");
        soft.assertEquals(fp.getPageFromUrlEndpoint(), expectedPage, "Test didn't finish on My Account page.");

        soft.assertAll();
    }


    @Test(groups = "password reset", dependsOnMethods = "testSuccessfulPasswordResetWithResetLink", enabled = false)
    public void testUnsuccessfulPasswordResetWithUsedResetLink() throws IOException {
        String expectedErrorMessage = "This key is invalid or has already been used. Please reset your password again if needed.";
        String expectedQueryString = "?show-reset-form=true";

        String resetLinkFromValidationEmail = nada.getLinkFromOldResetEmail(RESET_EMAIL_SUBJECT, email);
        getWindowManager().goToUrl(resetLinkFromValidationEmail);
        ForgotPasswordPage fp = new ForgotPasswordPage(driver);
        String actualErrorMessage = fp.getErrorMessage();
        String actualQueryStringInUrl = fp.getResetQueryStringFromEndpoint();

        soft.assertEquals(actualErrorMessage,expectedErrorMessage,"The correct error message is not displayed.");
        soft.assertEquals(actualQueryStringInUrl, expectedQueryString, "The expected query string could not be located in URL");

        soft.assertAll();
    }


    @Test(groups = "password reset", enabled=false)
    public void testUnsuccessfulPasswordResetPasswordsDoNotMatch(){

    }


    @Test(groups = "login", dependsOnMethods = "testSuccessfulRegistration", enabled=false)
    public void testSuccessfulLogin() {
        MyAccountPage ap = new MyAccountPage(driver);
        ap.setLoginUserName(username);
        extentParallel.get().info("Entered username " + username);
        ap.setLoginPassword(password);
        extentParallel.get().info("Entered password "+ password);
        ap.clickOnButton("login");
        extentParallel.get().info("Clicked on the login button");

        System.out.println(ap.getDashboardLinksNames());
        assertEquals(ap.getUsernameAfterLogin(), username, "Expected username is not displayed on dashboard.");
    }


    @Test(groups = "login", enabled = false)
    public void testRememberLoginDetails(){

    }


    @Test(groups = "login", enabled=false)
    public void testLogOut(){

    }


    @Test(groups = "login", dataProvider = "csvParser", dataProviderClass = CsvParser.class, enabled = false)
    public void testUnsuccessfulLogin(Map<String, String> testData) {

    }


}
