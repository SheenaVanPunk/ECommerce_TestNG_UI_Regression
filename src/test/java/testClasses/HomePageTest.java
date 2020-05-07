package testClasses;


import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class HomePageTest extends BaseTest {

    @Test(groups = "regression")
    @Parameters("expectedTextInSearchField")
    public void testVerifySearchTextField(String expectedTextInSearchField) {
        String actualText = hp.getSearchFieldText();

        assertTrue(actualText.contains(expectedTextInSearchField));
    }

    @Test(priority = 1, groups = "regression")
    public void testGetCartText() {
        String actualText = hp.getCartText();

        soft.assertTrue(actualText.contains("Cart"));
    }





}
