package testClasses;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.HomePageProductSection;
import pageObjects.MyWishlistPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class HomePageTest extends BaseTest {

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
