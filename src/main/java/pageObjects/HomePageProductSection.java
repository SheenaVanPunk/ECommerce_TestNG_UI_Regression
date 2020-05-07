package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import classesUtilities.Page;

import java.util.Random;

public class HomePageProductSection extends Page {
    private By products = By.cssSelector("div.noo-product-item");

    public HomePageProductSection(WebDriver driver) {
        super(driver);
    }

    public WebElement getRandomProduct(){
        Random random = new Random();
        return driver.findElements(products).get(random.nextInt(6));
    }

    public ProductPage clickOnRandomProduct() {
        var randomProduct = getRandomProduct();
        clickOnElement(randomProduct, randomProduct.getText().toUpperCase());
        return new ProductPage(driver);
    }

}
