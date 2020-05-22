package testClasses;

import org.testng.annotations.Test;
import pageObjects.ProductPage;

import java.util.List;

import static org.testng.Assert.*;
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

    @Test(enabled=false)
    public void testIsPromotionalPriceLowerThanOriginal(){
        pp = new ProductPage(driver);
        extentParallel.get().info("Comparing the original and promotional price - promotional must be lower.");

        assertTrue(pp.isPromotionalLower());
    }

    @Test
    public void testDoesTheMainPicChangeWhenClickedOnOtherThumbnail(){

    }

    @Test
    public void testAreColourAndSizeDropdownsAvailable(){
        var expectedResults = List.of(true, true, true);
        pp = new ProductPage(driver);
        extentParallel.get().info("Verify if a table with Colour and Size dropdowns with their labels is displayed.");
        var results = pp.areVariationsDisplayedAnEnabled();

       assertEquals(results, expectedResults, "Some of the three conditions is false. Conditions: is table with variations displayed, are both dropdowns enabled");
    }

    @Test
    public void testAddToCartButtonStates(){
        pp = new ProductPage(driver);
        boolean initialButtonState = pp.isCartButtonEnabled();
        pp.selectColour();
        pp.selectSize();
        boolean afterButtonState = pp.isCartButtonEnabled();

        assertFalse(initialButtonState);
        assertTrue(afterButtonState);
    }

    @Test
    public void testIsAtLeastOneOptionAvailableInDropdowns(){

    }


    @Test
    public void testCanUserAddProductToCart(){
        pp = new ProductPage(driver);
        pp.addProductToCart();
        int addedProductNo = pp.checkCartQuantity();
        double actualCartPrice = pp.checkCartPrice();

    }
}
