package testClasses;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Header;
import pageObjects.HomePageProductSection;
import pageObjects.MyWishlistPage;

import static org.testng.Assert.*;

public class HomePageTest extends BaseTest {

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

    @Test(groups = "regression")
    @Parameters("expectedTextInSearchField")
    public void testVerifySearchTextField(String expectedTextInSearchField) {
        String actualText = hp.getSearchFieldText();

        assertTrue(actualText.contains(expectedTextInSearchField));
    }

    @Test(groups = "regression")
    public void testGetCartText() {
        String actualText = hp.getCartText();

        soft.assertFalse(actualText.contains("Cart"));
    }

    @Test(enabled = false)
    public void testAddRandomNumberOfProductToWishlist(){
        HomePageProductSection hpps = new HomePageProductSection(driver);
        hp.scrollUntilHomePageSection("ladies");
      //  String selectedProduct = hpps.heartAProduct();

        MyWishlistPage wp = hpps.goToWishlist();
//        int actualSavedItems = wp.countSavedItems();
//        List<String> actualSavedProductNames = wp.getSavedProductsNames();
//
//        assertEquals(actualSavedItems, expectedSavedItems);
//        assertEquals(actualSavedProductNames, expectedSavedProductsNames);
    }





}
