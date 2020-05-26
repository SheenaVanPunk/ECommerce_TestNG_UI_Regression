package testClasses;

import org.testng.annotations.Test;
import pageObjects.ProductPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;
import static testUtilities.TestListener.extentParallel;

public class ProductPageTest extends BaseTest {
    ProductPage pp;

    @Test(priority = 1)
    public void testOpenProductPage(){

        assertTrue(pp.isAt());
    }

    @Test(groups = "visual")
    public void createBaselineImages(){
       pp = new ProductPage(driver);
       pp.toggleThroughGalleryImagesAndCompareSS();
    }

    @Test(groups = "visual", dependsOnMethods = "createBaselineImages")
    public void testConfirmThatAllGalleryImagesBelongToTheSameProduct(){
        pp = new ProductPage(driver);

        var expected = new ArrayList<Boolean>(Arrays.asList(new Boolean[pp.getThumbnailsNo()]));
        Collections.fill(expected, Boolean.TRUE);

        var imgsDisplayedOk = pp.toggleThroughGalleryImagesAndCompareSS();

        assertEquals(imgsDisplayedOk, expected);
    }

    @Test
    public void testIsPromotionalPriceLowerThanOriginal(){
        pp = new ProductPage(driver);
        extentParallel.get().info("Comparing the original and promotional price - promotional must be lower.");

        assertTrue(pp.isPromotionalLower());
    }

    @Test
    public void testDoesTheMainPicChangeWhenClickedOnMatchingThumbnail(){
        pp = new ProductPage(driver);
        var expected = new ArrayList<Boolean>(Arrays.asList(new Boolean[pp.getThumbnailsNo()]));
        Collections.fill(expected, Boolean.TRUE);

        var actual = pp.doSelectedThumbnailAndBigPhotoMatch();

        assertEquals(expected, actual, "There is a case where selected thumbnail image does not match the big image.");
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
        pp = new ProductPage(driver);

        soft.assertTrue(pp.selectSize());
        soft.assertTrue(pp.selectColour());

        soft.assertAll();
    }


    @Test
    public void testCanUserAddOneProductToCart(){
        pp = new ProductPage(driver);
        int expectedProductNo = 1;
        pp.addProductToCart(expectedProductNo);
        double expectedPrice = pp.getProductPrice() * expectedProductNo;
        int addedProductNo = pp.checkCartQuantity();
        double actualCartPrice = pp.checkCartPrice();

        soft.assertEquals(addedProductNo, expectedProductNo, "Cart is not showing the expected number of added products.");
        soft.assertEquals(actualCartPrice, expectedPrice, "Cart is not showing the correct price.");

        soft.assertAll();
    }

    @Test
    public void testCanUserMultipleProductsAndSeeCorrectPriceInCart(){
        pp = new ProductPage(driver);
        int expectedProductNo = 77;
        pp.addProductToCart(expectedProductNo);
        double expectedPrice = pp.getProductPrice() * expectedProductNo;
        int addedProductNo = pp.checkCartQuantity();
        double actualCartPrice = pp.checkCartPrice();

        soft.assertEquals(addedProductNo, expectedProductNo, "Cart is not showing the expected number of added products.");
        soft.assertEquals(actualCartPrice, expectedPrice, "Cart is not showing the correct price.");

        soft.assertAll();
    }
}
