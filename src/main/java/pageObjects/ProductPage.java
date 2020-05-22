package pageObjects;

import classesUtilities.Page;
import com.testautomationguru.ocular.Ocular;
import com.testautomationguru.ocular.comparator.OcularResult;
import com.testautomationguru.ocular.snapshot.Snap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Snap("productPageSnap.png")
public class ProductPage extends Page {

    private By sizeDropdown = By.id("pa_size");
    private By colourDropdown = By.id("pa_color");
    private By productFrame = By.cssSelector("div.single-product-content");
    private By productTitle = By.cssSelector("h1.product_title");
    private By relatedProductContainer = By.cssSelector("div.noo-product-item");
    private By productContent = By.cssSelector("div.single-product-content");
    //private By galleryImages = By.cssSelector("div.noo-woo-thumbnails__slide");
    private By originalPrice = By.cssSelector("p.price del");
    private By promotionalPrice = By.cssSelector("p.price ins");
    private By cartQuantity = By.cssSelector("span.cart-item span.cart-name-and-total");
    private By cartPrice = By.cssSelector("span.cart-item span.amount");

    @FindBy(css = "button.single_add_to_cart_button")
    private WebElement cartButton;

    @FindBy(css = "div.noo-woo-thumbnails__slide")
    private List<WebElement> thumbnails;

    @FindBy(css = "table.variations")
    private WebElement colourAndSizeDropdowns;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * loop through all thumbnails and click on each of them
     * get a thumbnail name and compare it with big image name
     * @return boolean - do names match
     */
//    @FindBy(css = )
//    private WebElement bigImage;
//
//    public List<Boolean> isEveryThumbnailEnlargedAfterClick(){
//        int i = 1;
//        List<Boolean> titleMatchingResults = new ArrayList<Boolean>();
//        for(WebElement thumbnail : thumbnails){
//            clickOnElement(thumbnail, "THUMBNAIL #" + i);
//            boolean titlesMatch = thumbnail.getAttribute("title").equals(bigImage.getAttribute("title"));
//            titleMatchingResults.add(titlesMatch);
//            i++;
//        }
//        return titleMatchingResults;
//    }

    public boolean isCartButtonEnabled(){
        scrollToProductSection();
        return !cartButton.getAttribute("class").contains("disabled");
    }

    public void addProductToCart(){
        selectColour();
        selectSize();
        clickOnElement(cartButton, "ADD TO CART BUTTON");
    }

    public void selectColour() {
        createSelectElement(colourDropdown).selectByIndex(1);
    }

    public void selectSize() {
        createSelectElement(sizeDropdown).selectByIndex(1);
    }

    public int getNumberOfOptionsForSize() {
        return createSelectElement(sizeDropdown).getOptions().size();
    }

    public boolean isAt() {
        String titleExtension = " – ToolsQA Demo Site";
        return getProductName()
                .toLowerCase()
                .concat(titleExtension)
                .contains(getPageTitle());
    }

    public String getProductName() {
        return getWebElementText(productTitle, "PRODUCT TITLE");
    }

    private List<WebElement> getAllRelatedProducts() {
        return driver.findElements(relatedProductContainer);
    }

    public boolean verifyImage(String fileName) {
        Path path = Paths.get(fileName);
        OcularResult result = Ocular.snapshot()
                .from(path)
                .sample()
                .using(driver)
                .compare();

        return result.isEqualsImages();
    }


    public List<Boolean> toggleThroughGalleryImagesAndCompareSS() {
        /**
         *  fetch the list of all gallery photos
         *  toogle through all of them
         *  take ss of each photo and compare it with previous
         */
        Map<String, String> fileNames = Map.of("img#1", "img1.png", "img#2",
                "img2.png", "img#3", "img3.png",
                "img#4", "img4.png", "img#5", "img5.png");

        List<Boolean> areImagesCorrectlyDisplayed = new ArrayList<Boolean>();
        scrollToProductSection();
        int i = 1;
        for (WebElement img : thumbnails) {
            String filename = fileNames.get("img#" + i);
            clickOnElement(img, "IMAGE #" + i);
            boolean isCorrectlyDisplayed = verifyImage(filename);
            areImagesCorrectlyDisplayed.add(isCorrectlyDisplayed);
            i++;
        }
        return areImagesCorrectlyDisplayed;
    }

    public void scrollToProductSection() {
        scrollUntilElement(getWebElement(productContent, "PRODUCT CONTENT SECTION"));
    }

    public double getPrice(By locator){
        String priceWithoutCurrency = driver.findElement(locator).getText().replace("₹", "");
        return Double.parseDouble(priceWithoutCurrency);
    }

    public boolean isPromotionalLower() {
        return getPrice(originalPrice) > getPrice(promotionalPrice);
    }

    public List<Boolean> areVariationsDisplayedAnEnabled() {
        return List.of(getWebElement(sizeDropdown, "SIZE DROPDOWN").isEnabled(),
                                    getWebElement(colourDropdown, "COLOUR DROPDOWN").isEnabled(),
                                    colourAndSizeDropdowns.isDisplayed());
        }

    public int checkCartQuantity() {
        String titleWithQuantity = driver.findElement(cartQuantity).getText().replace("Cart(", "").replace(")","");
        return Integer.parseInt(titleWithQuantity);
    }

    public double checkCartPrice(){
        return getPrice(cartPrice);
    }

    public class RelatedProduct {
        private By productTitle = By.cssSelector("div h3");
        private By productThumbnail = By.cssSelector("div.noo-product-thumbnail");
        private By productPrice = By.cssSelector("span.price");

        protected RelatedProduct getProduct() {
            getProductTitle();
            getProductThumbnail();
            getProductPrice();
            return new RelatedProduct();
        }

        private String getProductTitle() {
            return driver.findElement(productTitle).getText();
        }

        private WebElement getProductThumbnail() {
            return driver.findElement(productThumbnail);
        }

        private String getProductPrice() {
            return driver.findElement(productPrice).getText();
        }
    }
}
