package testClasses;

import classesUtilities.nadaEmailApiClasses.EmailMessage;
import classesUtilities.nadaEmailApiClasses.NadaEmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.annotations.Test;
import pageObjects.ForgotPasswordPage;
import pageObjects.MyAccountPage;
import pageObjects.WordPressPage;
import testUtilities.CsvParser;

import java.io.IOException;
import java.util.Map;

import static org.testng.Assert.*;
import static testUtilities.TestListener.extentParallel;

public class MyAccountTest extends BaseTest {
    private static NadaEmailService nada = new NadaEmailService();
    private String email = nada.getEmailId();

    @Test(dataProvider = "csvParser", dataProviderClass = CsvParser.class,groups = "user registration tests", enabled=false)
    public void testSuccessfulRegistration(Map<String, String> testData) throws JsonProcessingException, InterruptedException {
        String testCaseNo = testData.get("testCaseNo");
        String username = nada.generateUsername();
        String password = nada.generateUserPassword();
        String description = testData.get("description");

        MyAccountPage ap = new MyAccountPage(driver);
        extentParallel.get().info("Test Case no. " + testCaseNo + ": " + description);

        WordPressPage wp = ap.enterUserDataAndClickAButton(username, email, password);
        Thread.sleep(9000);
        String emailContent = nada.getMessageForEmailWithSubjectStartsWith("Your ToolsQA Demo Site account has been created!", email).getEmailContent();


        soft.assertEquals(wp.getActualPageTitle(), wp.getExpectedPageTitle(), "Registration process has not end" +
                "up on the WordPress page.");
        soft.assertTrue(emailContent.contains("Thanks for creating an account on ToolsQA Demo Site. Your username is"));
        soft.assertTrue(emailContent.contains(username));

        soft.assertAll();
    }


    @Test(dataProvider = "csvParser", dataProviderClass = CsvParser.class, groups = "user registration tests", enabled = false)
    public void testRegistrationErrorMessages(Map<String, String> testData){

        String testCaseNo = testData.get("testCaseNo");
        String username = testData.get("username");
        String email = testData.get("email");
        String password = testData.get("password");
        String description = testData.get("description");
        String expectedMessage = testData.get("message");
        extentParallel.get().info("Test Case no. " + testCaseNo + ": " + description);

        MyAccountPage ap = new MyAccountPage(driver);
        ap.enterUserDataAndClickAButton(username, email, password);
        String actualErrorMessage = ap.getErrorMessage();

        assertEquals(actualErrorMessage, expectedMessage, "The actual error message %s doesn't match expected %s"
                                                            .format(actualErrorMessage, expectedMessage));
    }

    @Test(dataProvider = "csvParser", dataProviderClass = CsvParser.class, groups = "user registration tests", enabled = false)
    public void testPasswordStrengthValidation(Map<String, String> testData){
        String testCaseNo = testData.get("testCaseNo");
        String description = testData.get("description");
        String password = testData.get("password");
        String expectedPasswordStrength = testData.get("validation");
        String expectedButtonState = testData.get("buttonState");

        extentParallel.get().info("Test Case no. " + testCaseNo + ": " + description);
        MyAccountPage ap = new MyAccountPage(driver);
            ap.setPassword(password); //type a password
            String actualPasswordStrength = ap.getPasswordStrengthAttribute(); //get password strength
            boolean actualPasswordValidatorPresence = ap.isPasswordValidatorPresent(); //get info if pwd validator is displayed
            String actualRegisterButtonState = ap.isRegisterButtonEnabled(); // get button state
            ap.clearPasswordField(); //clear field
            boolean actualPasswordValidatorAbsence = ap.isPasswordValidatorPresent();


            soft.assertTrue(actualPasswordValidatorPresence, "The password validator is not displayed"); //verify that the password validator shows up
            soft.assertEquals(actualPasswordStrength, expectedPasswordStrength, "Displayed validation level does not match the expected " +
                    expectedPasswordStrength); //verify if the password validation level is appropriate
            soft.assertEquals(actualRegisterButtonState, expectedButtonState, "The register button state does not match the expected"); //verify if register button is enabled
            soft.assertFalse(actualPasswordValidatorAbsence, "The password validator is still visible."); // verify that the password validator ribbon in not displayed anymore

            soft.assertAll();
    }

    @Test(dataProvider = "csvParser", dataProviderClass = CsvParser.class, groups = "user login tests", enabled = false)
    public void testUserLogin(Map<String, String> testData){

    }

    @Test(groups = "user login tests", enabled = false)
            //dependsOnMethods = "testSuccessfulRegistration" )

    public void testSendPasswordReset() throws InterruptedException, IOException {
        MyAccountPage ap = new MyAccountPage(driver);
        ForgotPasswordPage fp = ap.clickOnForgotPassword();
        fp.sendPasswordReset("1oe71nuwm0@getnada.com");

        Thread.sleep(7000);
        EmailMessage resetEmail = nada.getMessageForEmailWithSubjectStartsWith("Password Reset Request for ToolsQA Demo Site", "1oe71nuwm0@getnada.com");

        soft.assertTrue(fp.getConfirmationMessageText().contains("Password reset email has been sent."));
        soft.assertTrue(fp.getResetQueryStringFromEndpoint().contains("?reset-link-sent=true"));
        soft.assertNotNull(resetEmail);

        soft.assertAll();
    }

    @Test
    public void testResetPasswordWithResetLink() throws IOException {
        String newPassword = nada.generateUserPassword();

        String resetLinkFromValidationEmail = nada.getLinkFromValidationEmail("Password Reset Request for ToolsQA Demo Site", "1oe71nuwm0@getnada.com");
        getWindowManager().goToUrl(resetLinkFromValidationEmail); //click on a reset link
        ForgotPasswordPage fp = new ForgotPasswordPage(driver);
        fp.setNewPassword(newPassword);
        fp.repeatNewPassword(newPassword);
        MyAccountPage ap = fp.saveNewPassword();

        assertTrue(ap.getErrorMessage().contains("Your password has been reset successfully."));
        assertTrue(fp.getResetQueryStringFromEndpoint().contains("?password-reset=true"));
    }

    @Test(dataProvider = "csvParser", dataProviderClass = CsvParser.class)
    public void testUnsuccessfulPasswordResetAttempts(Map<String, String> testData){
        String invalidUserID = testData.get("invalidEmailOrUsername");
        String expectedErrorMessage = testData.get("message");

        MyAccountPage ap = new MyAccountPage(driver);
        ForgotPasswordPage fp = ap.clickOnForgotPassword();
        fp.sendPasswordReset(invalidUserID);

        assertEquals(fp.getErrorMessage(), expectedErrorMessage, "A correct error message is not displayed.");
    }
    /*

    2 - new user registration
        - set ne user credentials
        - go to email and confirm the presence of the welcome email "Welcome to ToolsQA Demo Site"
        Hi Kokoska@34567,
        Thanks for creating an account on ToolsQA Demo Site. Your username is Kokoska@34567. You can access your account area to view orders, change your password, and more at: http://shop.demoqa.com/my-account/
        We look forward to seeing you soon.

     */

}
