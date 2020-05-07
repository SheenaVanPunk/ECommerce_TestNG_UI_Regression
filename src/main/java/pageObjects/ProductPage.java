package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import classesUtilities.Page;

import java.util.Random;

public class ProductPage extends Page {
    private By sizeDropdown = By.id("pa_size");
    private By productFrame = By.cssSelector("div.single-product-content");

    public ProductPage(WebDriver driver){
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


    public class RelatedProducts{}
}
