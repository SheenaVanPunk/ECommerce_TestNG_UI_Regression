package pageObjects;

import org.openqa.selenium.By;
import utilities.Page;

public class ProductPage extends Page {
    private By sizeDropdown = By.id("pa_size");
    private By productFrame = By.cssSelector("div.single-product-content");

    public ProductPage(){
        super(driver);
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



    public class RelatedProducts{}
}
