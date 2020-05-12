package testClasses;

import org.testng.annotations.Test;
import pageObjects.MyAccountPage;
import pageObjects.WordPressPage;
import testUtilities.CsvParser;

import java.util.Map;

import static org.testng.Assert.*;
import static testUtilities.TestListener.extentParallel;

public class MyAccountTest extends BaseTest{

    @Test(dataProvider = "csvParser", dataProviderClass = CsvParser.class)
    public void testSuccessfulRegistration(Map<String, String> testData){
        String testCaseNo = testData.get("testCaseNo");
        String username = testData.get("username");
        String email = testData.get("email");
        String password = testData.get("password");
        String description = testData.get("description");

        MyAccountPage ap = new MyAccountPage(driver);
        extentParallel.get().info("Test Case no. " + testCaseNo + ": " + description);

        WordPressPage wp = ap.enterUserDataAndClickAButton(username, email, password);

        assertEquals(wp.getActualPageTitle(), wp.getExpectedPageTitle(), "Registration process has not end" +
                "up on the WordPress page.");
    }


    @Test(dataProvider = "csvParser", dataProviderClass = CsvParser.class)
    public void testErrorMessages(Map<String, String> testData){

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

    @Test(dataProvider = "csvParser", dataProviderClass = CsvParser.class)
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
}
