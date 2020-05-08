package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import classesUtilities.Page;

import java.util.Random;

public class HomePageProductSection extends Page {
    private By productsTitles = By.cssSelector("div.noo-product-item");
    private By heartIcon = By.cssSelector("");
    private By wishlistLink = By.linkText("My Wishlist");

    public HomePageProductSection(WebDriver driver) {
        super(driver);
    }

    public WebElement getRandomProduct(){
        Random random = new Random();
        return driver.findElements(productsTitles).get(random.nextInt(6));
    }

    public ProductPage clickOnRandomProduct() {
        var randomProduct = getRandomProduct();
        clickOnElement(randomProduct, randomProduct.getText().toUpperCase());
        return new ProductPage(driver);
    }

    public String heartAProduct(){
        hoverOverProduct();
        clickOnElement(heartIcon, "HEART ICON");
        verifyHeartIconStyleChange();
        return getProductsName();

    }

    private String getProductsName() {
        return null;
    }

    private void verifyHeartIconStyleChange() {

    }

    private void hoverOverProduct() {

    }


    public MyWishlistPage goToWishlist() {
        clickOnElement(wishlistLink, "LINK TO MY WISHLIST");
        return new MyWishlistPage(driver);
    }
}
