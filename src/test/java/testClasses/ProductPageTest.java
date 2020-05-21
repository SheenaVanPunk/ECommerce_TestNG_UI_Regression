package testClasses;

import org.testng.annotations.Test;
import pageObjects.ProductPage;

import static org.testng.Assert.assertTrue;

public class ProductPageTest extends BaseTest {
    ProductPage pp;

    @Test(enabled=false)
    public void testOpenProductPage(){
        pp = hp.clickOnARandomProduct();
        pp.compareSS();

        assertTrue(pp.isAt());
        //assertEquals(pp.compareSS());

    }

    @Test
    public void compareGalleryImages(){
        pp= new ProductPage(driver);
        pp.scrollToProductSection();
        pp.compareSS();
    }
}
