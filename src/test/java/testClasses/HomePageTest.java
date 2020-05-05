package testClasses;


import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.ProductPage;
import pageObjects.SearchPage;

import static org.testng.Assert.*;


public class HomePageTest extends BaseTest {
    SoftAssert soft = new SoftAssert();

    @Test (groups = "proba")
    public void testSearchForItem() {
        String actualText1 = hp.getSearchFieldText();
        var itemForSearch = "dress";
        SearchPage sp = hp.searchForAnItem(itemForSearch);

        soft.assertTrue(actualText1.contains("Search"));
        assertTrue(sp.getPageTitle().contains(itemForSearch));
        soft.assertAll();
    }

    @Test (priority = 1, groups = "proba")
    public void testGetCartText(){
        String actualText = hp.getCartText();


        soft.assertTrue(actualText.contains("Cart"));
    }

    @Test (groups = "proba")
    public void selectSizeForAProduct(){
        ProductPage pp = hp.clickOnRandomProduct();
        String ppTitle = pp.getPageTitle();

        String size = "44";
        pp.selectSizeFromDropdown(size);

        soft.assertTrue(ppTitle.contains("playboy"));
        soft.assertTrue(pp.isSelectedOptionEqualTo(size));
    }

    @Test (groups = "proba")
    public void testTryinOut() throws InterruptedException {

        var sectionTitle = hp.scrollUntilFashionNews("reviews");

        assertTrue(sectionTitle.toLowerCase().contains("fashion news"));

    }
}
