package pageObjects;

import classesUtilities.Page;
import com.testautomationguru.ocular.Ocular;
import com.testautomationguru.ocular.comparator.OcularResult;
import com.testautomationguru.ocular.snapshot.Snap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

@Snap("productPageSnap.png")
public class ProductPage extends Page {
    private By sizeDropdown = By.id("pa_size");
    private By productFrame = By.cssSelector("div.single-product-content");
    private By productTitle = By.cssSelector("h1.product_title");
    private By relatedProductContainer = By.cssSelector("div.noo-product-item");
    private By productContent = By.cssSelector("div.single-product-content");

    public ProductPage(WebDriver driver){
        super(driver);
    }

    public OcularResult compareSS(){
        return Ocular.snapshot().from(ProductPage.class)
                      .sample().using(driver)
                      .compare();
    }

    public void selectSizeFromDropdown(String text){
        scrollUntilElement(getWebElement(productFrame, "PRODUCT FRAME"));
        selectByText(sizeDropdown, text, "SIZE");
    }

    public Boolean isSelectedOptionEqualTo(String value) {
        return createSelectElement(sizeDropdown).getAllSelectedOptions()
                .get(0)
                .getText()
                .equals(value);
    }

    public int getNumberOfSizeOptions(){
        return createSelectElement(sizeDropdown).getOptions().size();
    }

    public String getRandomOption(){
        Random random = new Random();
        int randomIndex = random.nextInt(3);
        scrollUntilElement(getWebElement(sizeDropdown, "SIZE DROPDOWN"));
        String option = createSelectElement(sizeDropdown).getOptions().get(randomIndex).getText();
        return option;
    }

    public void selectRandomOptionFromDropdown(){
        selectByText(sizeDropdown, getRandomOption(), "SIZE DROPDOWN");
    }

    public String getProductPageTitle(){
        return getPageTitle();
    }

    public boolean isAt(){
        String titleExtension = " – ToolsQA Demo Site";
        return getProductName()
                .toLowerCase()
                .concat(titleExtension)
                .contains(getPageTitle());
    }

    public String getProductName(){
        return getWebElementText(productTitle, "PRODUCT TITLE");
    }

    private List<WebElement> getAllRelatedProducts(){
        return driver.findElements(relatedProductContainer);
    }
//  this method has a problem with comparing WebElement and RelatedProduct, latter cannot be casted to product
//    public boolean checkIFAllProductsHaveImages(){
//        List<Boolean> isImgShown = new ArrayList<Boolean>();
//        scrollUntilElement(driver.findElement(By.cssSelector(".title-related")));
//        for(RelatedProduct product : getAllRelatedProducts()){
//            boolean imgShown = product.getProductThumbnail().isDisplayed();
//            isImgShown.add(imgShown);
//        }
//        boolean allShown = isImgShown.listIterator().hasNext() == true ? true : false;
//        return allShown;
//    }

    public void toggleThroughGalleryImages(){
        //fetch the list of all gallery photos
        //toogle through all of them
        //take ss of each photo and compare it with previous
    }

    public void scrollToProductSection(){
        scrollUntilElement(driver.findElement(productContent));
    }


    public class RelatedProduct{
        private By productTitle = By.cssSelector("div h3");
        private By productThumbnail = By.cssSelector("div.noo-product-thumbnail");
        private By productPrice = By.cssSelector("span.price");

        protected RelatedProduct getProduct(){
            getProductTitle();
            getProductThumbnail();
            getProductPrice();
            return new RelatedProduct();
        }

        private String getProductTitle(){
            return driver.findElement(productTitle).getText();
        }

        private WebElement getProductThumbnail() {
            return driver.findElement(productThumbnail);
        }

        private String getProductPrice(){
            return driver.findElement(productPrice).getText();
        }
    }
}
