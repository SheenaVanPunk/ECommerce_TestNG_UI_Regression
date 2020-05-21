package testClasses;

import org.testng.annotations.Test;
import pageObjects.ProductPage;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static testUtilities.TestListener.extentParallel;

public class ProductPageTest extends BaseTest {
    ProductPage pp;

    @Test(enabled=false)
    public void testOpenProductPage(){
        pp = hp.clickOnARandomProduct();

        assertTrue(pp.isAt());
    }

    @Test(enabled = false)
    public void createBaselineImages(){
       pp = new ProductPage(driver);
       pp.toggleThroughGalleryImagesAndCompareSS();
    }

    @Test(dependsOnMethods = "createBaselineImages", enabled = false)
    public void testConfirmThatAllGalleryImagesBelongToTheSameProduct(){
        var expectedBehavior = List.of(true, true, true, true, true);

        pp = new ProductPage(driver);
        var imgsDisplayedOk = pp.toggleThroughGalleryImagesAndCompareSS();

        assertEquals(imgsDisplayedOk, expectedBehavior);
    }

    @Test
    public void testIsPromotionalPriceLowerThanOriginal(){
        pp = new ProductPage(driver);
        extentParallel.get().info("Comparing the original and promotional price - promotional must be lower.");

        assertTrue(pp.isPromotionalLower());
    }

    @Test
    public void testDoesTheMainPicChangeWhenClickedOnOtherThumbnail(){

    }

    @Test
    public void testIsAddToCartButtonDisabled(){}

    @Test
    public void testAreColourAndSizeDropdownsAvailable(){}

    @Test
    public void testCanUserAddProductToCart(){}
}
