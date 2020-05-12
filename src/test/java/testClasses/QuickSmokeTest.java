package testClasses;

import org.testng.annotations.Test;
import pageObjects.Header;
import pageObjects.HomePageProductSection;
import pageObjects.ProductPage;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertNull;

public class QuickSmokeTest extends BaseTest {
    private HomePageProductSection ps;

    @Test(groups = "smoke", priority = 1)
    public void testIsTheWebSiteLoadingTheHomePage() {

        assertNotNull(hp);
    }

    @Test(groups = "smoke")
    public void testIsDisclaimerCoveringHeader() {
        Header header = new Header(driver);

        assertFalse(header.isDisclaimerDisplayed());
    }

    @Test(groups = "smoke", enabled = false)
    public void selectSizeForARandomProduct() {
        ps = new HomePageProductSection(driver);
        ProductPage pp = ps.clickOnRandomProduct();
        String ppTitle = pp.getProductPageTitle();
        String size = pp.getRandomOption();
        pp.selectSizeFromDropdown(size);

        soft.assertTrue(pp.isSelectedOptionEqualTo(size));
    }
}
