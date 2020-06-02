package testClasses;

import classesUtilities.nadaEmailApiClasses.NadaEmailService;
import org.testng.annotations.Test;
import pageObjects.DashboardPage;
import pageObjects.ForgotPasswordPage;
import pageObjects.MyAccountPage;
import pageObjects.WordPressPage;
import testUtilities.CsvParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static testUtilities.TestListener.extentParallel;

public class MyAccountTest extends BaseTest {

    private static NadaEmailService nada = new NadaEmailService();
    private MyAccountPage ap;
    private ForgotPasswordPage fp;
    private DashboardPage dp;
    private WordPressPage wp;
    private String email = nada.getEmailId();
    private String username = nada.generateUsername();
    private String password = nada.generateUserPassword();

    private static final String RESET_EMAIL_SUBJECT = "Password Reset Request for ToolsQA Demo Site";
    private static final String REGISTRATION_EMAIL_SUBJECT = "Your ToolsQA Demo Site account has been created!";
    private static final String RESET_EMAIL_TITLE = "Password Reset Request";
    private static final String REGISTRATION_EMAIL_TITLE = "Welcome to ToolsQA Demo Site";
    private static final String PASSWORD_RESET_CONFIRM_QUERY = "?password-reset=true";


    @Test(priority = 4, groups = "registration")
    public void testSuccessfulRegistration() {
        ap = new MyAccountPage(driver);
        extentParallel.get().info("Test Case no. 1: Valid user data");

        wp = ap.enterUserDataAndClickLoginButton(username, email, password);
        extentParallel.get().info("Submitted new user credentials: " + username + " " + email + " " + password);

        String emailContent = nada.getNewMessageWithSubject(REGISTRATION_EMAIL_SUBJECT, email).getEmailContent();
        extentParallel.get().info("Fetched the validation email with subject " + "\"" + REGISTRATION_EMAIL_SUBJECT + "\"");

        soft.assertEquals(wp.getActualPageTitle(), wp.getExpectedPageTitle(), "Registration process has not end" +
                "up on the WordPress page.");
        soft.assertTrue(emailContent.contains(REGISTRATION_EMAIL_TITLE), "Email doesn't contain the expected title " + "\"" + REGISTRATION_EMAIL_TITLE + "\"");
        soft.assertTrue(emailContent.contains(username), "Email doesn't contain the correct username " + "\"" + username + "\"");

        soft.assertAll();
    }


    @Test(priority = 1, dataProvider = "csvParser", dataProviderClass = CsvParser.class, groups = "registration")
    public void testRegistrationErrors(Map<String, String> testData) {
        String testCaseNo = testData.get("testCaseNo");
        String username = testData.get("username");
        String email = testData.get("email");
        String password = testData.get("password");
        String description = testData.get("description");
        String expectedMessage = testData.get("message");

        extentParallel.get().info("Test Case no. " + testCaseNo + ": " + description);

        ap = new MyAccountPage(driver);
        ap.enterUserDataAndClickLoginButton(username, email, password);
        extentParallel.get().info("Submitted user credentials: " + "\"" + username + "\", " + "\"" + email + "\", " + "\"" + password + "\"");
        String actualErrorMessage = ap.getErrorMessage();
        extentParallel.get().info("Fetched the error message: " + actualErrorMessage);

        assertEquals(actualErrorMessage, expectedMessage, "The actual error message doesn't match expected.");
    }


    @Test(priority = 2, groups = "registration", dataProvider = "csvParser", dataProviderClass = CsvParser.class)
    public void testPasswordStrengthValidation(Map<String, String> testData) {
        String testCaseNo = testData.get("testCaseNo");
        String description = testData.get("description");
        String password = testData.get("password");
        String expectedPasswordStrength = testData.get("validation");
        String expectedButtonState = testData.get("buttonState");
        String buttonType = "register";

        extentParallel.get().info("Test Case no. " + testCaseNo + ": " + description);
        ap = new MyAccountPage(driver);
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


    @Test(priority = 9, groups = "password reset", dependsOnMethods = "testSuccessfulLogin")
    public void testSuccessfulResetEmailSent() throws IOException {
        String expectedValidationMessage = "Password reset email has been sent.";
        String expectedQueryString = "?reset-link-sent=true";

        ap = new MyAccountPage(driver);
        fp = ap.clickOnForgotPassword();
        extentParallel.get().info("Scrolled to the login section and clicked on LOST YOUR PASSWORD link");
        fp.sendPasswordReset(email);
        extentParallel.get().info("Entered the registered email address " + email + " and sent a password reset e-mail");

        String resetLink = nada.getNewLinkFromResetEmail(RESET_EMAIL_SUBJECT, email);
        String emailContent = nada.getNewMessageWithSubject(RESET_EMAIL_SUBJECT, email).getEmailContent();
        extentParallel.get().info("Fetched the password reset email.");

        soft.assertTrue(fp.getConfirmationMessageText().contains(expectedValidationMessage), "The validation message is not displayed.");
        soft.assertTrue(fp.getQueryStringFromEndpoint().contains(expectedQueryString), "The query string is not added to URL.");
        soft.assertTrue(emailContent.contains(RESET_EMAIL_TITLE), "The email title is not " + RESET_EMAIL_TITLE);
        soft.assertFalse(resetLink.isEmpty(), "The URL could not be found in password reset e-mail.");

        soft.assertAll();
    }


    @Test(priority = 6, groups = "password reset", dataProvider = "csvParser", dataProviderClass = CsvParser.class)
    public void testUnsuccessfulResetEmailSent(Map<String, String> testData) {
        String invalidUserID = testData.get("invalidEmailOrUsername");
        String expectedErrorMessage = testData.get("message");

        ap = new MyAccountPage(driver);
        fp = ap.clickOnForgotPassword();
        extentParallel.get().info("Clicked on the LOST YOUR PASSWORD link");
        fp.sendPasswordReset(invalidUserID);
        extentParallel.get().info("Entered invalid username/e-mail: " + invalidUserID);
        String actualErrorMessage = fp.getErrorMessages().get(0);

        assertEquals(actualErrorMessage, expectedErrorMessage, "A correct error message is not displayed.");
        extentParallel.get().info("Displayed error message is correct: " + actualErrorMessage);
    }


    @Test(priority = 8, groups = "password reset", dependsOnMethods = "testSuccessfulResetEmailSent")
    public void testSuccessfulPasswordResetWithResetLink() throws IOException {
        String newPassword = nada.generateUserPassword();
        String expectedValidationMessage = "Your password has been reset successfully.";
        String expectedPage = "my-account";

        String resetLinkFromValidationEmail = nada.getNewLinkFromResetEmail(RESET_EMAIL_SUBJECT, email);
        extentParallel.get().info("Fetched the reset link from received e-mail: " + resetLinkFromValidationEmail);
        getWindowManager().goToUrl(resetLinkFromValidationEmail); //click on a reset link
        extentParallel.get().info("Opened the reset link in a browser.");
        fp = new ForgotPasswordPage(driver);
        fp.setNewPasswordInField(newPassword, 1);
        extentParallel.get().info("Entered new password for the 1st time: " + newPassword);
        fp.setNewPasswordInField(newPassword, 2);
        extentParallel.get().info("Repeated new password.");
        ap = fp.saveNewPassword();
        extentParallel.get().info("Saved new password.");

        soft.assertTrue(ap.getValidationMessage().contains(expectedValidationMessage), "The expected validation message is not displayed.");
        soft.assertTrue(fp.getQueryStringFromEndpoint().contains(PASSWORD_RESET_CONFIRM_QUERY), "The password reset confirmation query string cannot be located in URL.");
        soft.assertEquals(fp.getPageFromUrlEndpoint(), expectedPage, "Test didn't finish on My Account page.");

        soft.assertAll();
    }


    @Test(priority = 10, groups = "password reset", dependsOnMethods = "testSuccessfulPasswordResetWithResetLink", enabled = false)
    public void testUnsuccessfulPasswordResetWithUsedResetLink() throws IOException {
        String expectedErrorMessage = "This key is invalid or has already been used. Please reset your password again if needed.";
        String expectedQueryString = "?show-reset-form=true";

        String resetLinkFromValidationEmail = nada.getLinkFromOldResetEmail(RESET_EMAIL_SUBJECT, email);
        getWindowManager().goToUrl(resetLinkFromValidationEmail);
        fp = new ForgotPasswordPage(driver);
        String actualErrorMessage = fp.getErrorMessages().get(0);
        String actualQueryStringInUrl = fp.getQueryStringFromEndpoint();

        soft.assertEquals(actualErrorMessage, expectedErrorMessage, "The correct error message is not displayed.");
        soft.assertEquals(actualQueryStringInUrl, expectedQueryString, "The expected query string could not be located in URL");

        soft.assertAll();
    }


    @Test(priority = 12, groups = "password reset", dataProvider = "csvParser", dataProviderClass = CsvParser.class)
    public void testUnsuccessfulCreateNewPasswordAttempts(Map<String, String> testData) throws IOException {
        String testCaseNo = testData.get("testCaseNo");
        String newPassword = testData.get("newPassword");
        String repeatPassword = testData.get("repeatPassword");
        String expectedErrorMessage = testData.get("message");
        String description = testData.get("description");
        String expectedQueryString = "?show-reset-form=true";
        String expectedPage = "lost-password";

        extentParallel.get().info("Test Case no. " + testCaseNo + ": " + description);
        ap = new MyAccountPage(driver);
        fp = ap.clickOnForgotPassword();
        fp.sendPasswordReset(email);
        getWindowManager().goToUrl(nada.getNewLinkFromResetEmail(RESET_EMAIL_SUBJECT, email));
        extentParallel.get().info("Opened reset password link in browser.");
        fp.setNewPasswordInField(newPassword, 1); //send a String to new password field
        fp.setNewPasswordInField(repeatPassword, 2); //send a String to repeat password field
        extentParallel.get().info("Entered new password " + "\"" + newPassword + "\"" +
                " and repeat password " + "\"" + repeatPassword + "\"");
        fp.saveNewPassword();
        extentParallel.get().info("Clicked on SAVE button");

        String actualErrorMessage = String.join("", fp.getErrorMessages());
        String actualQueryString = fp.getQueryStringFromEndpoint();
        String actualPage = fp.getPageFromUrlEndpoint();

        soft.assertEquals(actualErrorMessage, expectedErrorMessage, "The correct error message is not displayed.");
        soft.assertEquals(actualQueryString, expectedQueryString, "Expected query string cannot be located in URL.");
        soft.assertEquals(actualPage, expectedPage, "Test didn't finish on the expected page.");

        soft.assertAll();
    }


    @Test(priority = 5, groups = "login", dependsOnMethods = "testSuccessfulRegistration")
    public void testSuccessfulLogin() {
        int linksOnDashboardNo = 6;
        var expectedLinksOnDashboard = List.of("Dashboard", "Orders", "Downloads", "Addresses", "Account details", "Logout");
        ap = new MyAccountPage(driver);
        dp = ap.enterUserDataAndClickLoginButton(email, password);

        extentParallel.get().info("Entered email " + "\"" + email + "\", " + "and password " + "\"" + password + "\"" + ". Login data submitted.");
        var usernameOnDashboard = dp.getUsernameAfterLogin();
        var linksOnDashboard = dp.getDashboardLinksNames();
        extentParallel.get().info("After log in, username displayed on the dashboard is" + "\"" + usernameOnDashboard + "\"" + ".");
        extentParallel.get().info("Displayed are links: " + "\"" + linksOnDashboard + "\"" + ".");

        soft.assertEquals(usernameOnDashboard, username, "Expected username is not displayed on dashboard.");
        soft.assertEquals(linksOnDashboard.size(), linksOnDashboardNo, "6 links are not displayed on the left side of the screen.");
        soft.assertEquals(linksOnDashboard, expectedLinksOnDashboard, "The expected links are not displayed on dashboard.");

        soft.assertAll();
    }


    @Test(priority = 6, groups = "login")
    public void testRememberLoginDetails() {
        String authType = "login";
        ap = new MyAccountPage(driver);
        ap.enterEmailAndPassword(email, password);
        extentParallel.get().info("Entered username " + "\"" + username + "\", " + "and password " + "\"" + password + "\".");
        boolean isCheckboxSelected = ap.clickOnCheckbox();
        extentParallel.get().info("Clicked on Remember me checkbox.");
        dp = ap.clickOnLoginButton();
        extentParallel.get().info("Login data submitted.");

        ap = dp.logOut();
        extentParallel.get().info("Logged out.");
        String savedUsernameOnLoginForm = ap.getUsernameFromField();

        soft.assertTrue(isCheckboxSelected, "Checkbox Remember me was not selected.");
        soft.assertEquals(savedUsernameOnLoginForm, username, "Username is not remembered on login form.");

        soft.assertAll();
    }


    @Test(priority = 7, groups = "login")
    public void testLogOut() {
        ap = new MyAccountPage(driver);
        dp = ap.enterUserDataAndClickLoginButton(email, password);
        extentParallel.get().info(String.format("Entered username %s and password %s and submitted them.", email, password));
        ap = dp.logOutWithLinkInsideWelcomeText();
        extentParallel.get().info("Clicked on Log Out link inside the welcome text.");

        assertTrue(ap.onMyAccountPage(), "The test didn't return to login page.");
    }


    @Test(priority = 3, groups = "login", dataProvider = "csvParser", dataProviderClass = CsvParser.class)
    public void testLoginErrors(Map<String, String> testData) {
        String testCaseNo = testData.get("testCaseNo");
        String userId = testData.get("usernameOrEmail");
        String password = testData.get("password");
        String expectedErrorMessage = testData.get("message");
        String description = testData.get("description");

        extentParallel.get().info("Test Case no. " + testCaseNo + ": " + description);
        ap = new MyAccountPage(driver);
        ap.enterUserDataAndClickLoginButton(userId, password);
        extentParallel.get().info(String.format("Submitted User ID %s and password %s.", userId, password));
        String actualErrorMessage = ap.getErrorMessage();

        soft.assertTrue(ap.onMyAccountPage());
        soft.assertEquals(actualErrorMessage, expectedErrorMessage, "The expected error message is not displayed.");

        soft.assertAll();;
    }

    @Test(priority = 11, groups = "login", dependsOnMethods = "testSuccessfulPasswordResetWithResetLink")
    public void testUnsuccessfulLoginWithOldPasswordAfterReset() {
        String expectedErrorMessage = "ERROR: The username or password you entered is incorrect. Lost your password?";
        ap = new MyAccountPage(driver);

        ap.enterUserDataAndClickLoginButton(username, password);
        extentParallel.get().info(String.format("Submitted username %s and password %s.", username, password));
        String actualErrorMessage = ap.getErrorMessage();
        extentParallel.get().info("Displayed is the error message: "+ "\"" + actualErrorMessage + "\"");

        ap.clearUsernameLoginField();
        ap.enterUserDataAndClickLoginButton(email,password);
        extentParallel.get().info(String.format("Submitted email %s and password %s.", email, password));
        String actualErrorMessage1 = ap.getErrorMessage();
        extentParallel.get().info("Displayed is the error message: "+ "\"" + actualErrorMessage1 + "\"");

        soft.assertEquals(actualErrorMessage, actualErrorMessage1,"Error messages are not equal when trying to" +
                "log in with old password using e-mail and trying to do the same with username.");
        soft.assertEquals(actualErrorMessage, expectedErrorMessage, "The expected error message is not displayed.");

        soft.assertAll();
    }




}
