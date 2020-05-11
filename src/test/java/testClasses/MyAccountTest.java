package testClasses;

import org.testng.annotations.Test;
import pageObjects.MyAccountPage;
import pageObjects.WordPressPage;
import testUtilities.CsvParser;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;
import static testUtilities.TestListener.extentParallel;

public class MyAccountTest extends BaseTest{

    @Test(dataProvider = "csvParser", dataProviderClass = CsvParser.class, enabled = false)
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

    @Test(dataProvider = "csvParser", dataProviderClass = CsvParser.class, enabled = false)
    public void testUnsuccessfulRegistration(Map<String, String> testData){
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

        extentParallel.get().info("Test Case no. " + testCaseNo + ": " + description);
        MyAccountPage ap = new MyAccountPage(driver);
        ap.setPassword(password);
        boolean actualPasswordValidatorPresence = ap.isPasswordValidatorPresent();
        String passwordStrength = ap.getPasswordStrengthAttribute();
        boolean actualRegisterButtonState = ap.isRegisterButtonEnabled();
        ap.clearPasswordField();
        boolean actualPasswordValidatorAbsence = ap.isPasswordValidatorPresent();
        /*
        type a password
        verify that the password validator shows
        get password strength
        verify if register button is enabled
        clear field
        verify that the password validator ribbon in not displayed anymore
        repeat
         */
        soft.assertTrue(passwordValidator.isDisplayed(), "The password validator is not displayed");
        soft.assertEquals(registerButton.isEnabled(), expectedButtonState, "The register button state does not match the expected "
                + expectedState);
        soft.assertFalse(passwordValidator.isDisplayed(), "The password validator is still visible.");
    }
}
