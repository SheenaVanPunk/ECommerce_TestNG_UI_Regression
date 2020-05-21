package pageObjects;

import classesUtilities.Page;
import com.testautomationguru.ocular.Ocular;
import com.testautomationguru.ocular.comparator.OcularResult;
import com.testautomationguru.ocular.snapshot.Snap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Snap("productPageSnap.png")
public class ProductPage extends Page {
    private By sizeDropdown = By.id("pa_size");
    private By productFrame = By.cssSelector("div.single-product-content");
    private By productTitle = By.cssSelector("h1.product_title");
    private By relatedProductContainer = By.cssSelector("div.noo-product-item");
    private By productContent = By.cssSelector("div.single-product-content");
    private By galleryImages = By.cssSelector("div.noo-woo-thumbnails__slide");
    private By originalPrice = By.cssSelector("p.price del");
    private By promotionalPrice = By.cssSelector("p.price ins");


    public ProductPage(WebDriver driver) {
        super(driver);
        //PageFactory.initElements(driver, this);
    }

    public void selectSizeFromDropdown(String text) {
        scrollUntilElement(getWebElement(productFrame, "PRODUCT FRAME"));
        selectByText(sizeDropdown, text, "SIZE");
    }

    public Boolean isSelectedOptionEqualTo(String value) {
        return createSelectElement(sizeDropdown).getAllSelectedOptions()
                .get(0)
                .getText()
                .equals(value);
    }

    public int getNumberOfSizeOptions() {
        return createSelectElement(sizeDropdown).getOptions().size();
    }

    public String getRandomOption() {
        Random random = new Random();
        int randomIndex = random.nextInt(3);
        scrollUntilElement(getWebElement(sizeDropdown, "SIZE DROPDOWN"));
        return createSelectElement(sizeDropdown).getOptions().get(randomIndex).getText();
    }


    public String getProductPageTitle() {
        return getPageTitle();
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
        List<WebElement> imgs = driver.findElements(galleryImages);
        int i = 1;

        for (WebElement img : imgs) {
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
