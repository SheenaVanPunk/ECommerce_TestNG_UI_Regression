package testClasses;

import org.testng.annotations.Test;
import pageObjects.Header;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class QuickSmokeTest extends BaseTest{

    @Test (groups = "smoke", priority = 1)
    public void testIsTheWebSiteLoadingTheHomePage(){

        assertNotNull(hp);
    }

    @Test (dependsOnMethods = "testIsTheWebSiteLoadingTheHomePage")
    public void testIsDisclaimerCoveringHeader(){
        Header header = new Header();

        assertTrue(header.isDisclaimerDisplayed());
    }
}
