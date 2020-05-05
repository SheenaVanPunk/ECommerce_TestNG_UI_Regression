package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.StepsLogger;
import utilities.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomePage extends Page {
    private By searchField = By.cssSelector("a.noo-search");
    private By searchPopUp = By.cssSelector("input.form-control");
    private By cartIcon = By.cssSelector("span.cart-name-and-total");
    private By secondProduct = By.xpath("//a[contains(text(), 'playboy')]");
    private By homePageSections = By.cssSelector("div.noo-container-fluid");
    private By productsList = By.cssSelector("div.noo-product-item");
    private By fashionNewsSectionTitle = By.cssSelector("div.noo-shblog-header");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getCartText() {
        getPageTitle();
        return getWebElementText(cartIcon, "CART");
    }

    public String getSearchFieldText() {
        return getWebElementText(searchField, "SEARCH FIELD");
    }

    public SearchPage searchForAnItem(String text) {
        clickOnElement(searchField, "SEARCH FIELD");
        sendText(searchPopUp, "SEARCH FORM", text + Keys.ENTER);
        return new SearchPage();
    }

    public ProductPage clickOnRandomProduct() {
        clickOnElement(secondProduct, driver.findElement(secondProduct).getText().toUpperCase());
        return new ProductPage();
    }

    private Map<String, WebElement> createPageSectionsMap() {
        List<String> sectionNames = List.of("top_banner", "four_icons", "products_girl",
                "products_boy", "reviews",
                "fashion_news", "sign_up_banner");
        List<WebElement> sections = driver.findElements(homePageSections);
        Map<String, WebElement> sectionsMap = new HashMap<>();
        for (String sectionName : sectionNames) {
            WebElement section = sections.listIterator().next();
            sectionsMap.put(sectionName, section);
        }
        new StepsLogger().info("Home page sections are: " + sectionsMap.keySet().toString().toUpperCase());
        return sectionsMap;
    }

    public List<WebElement> getProductsList() {
        return driver.findElements(productsList);
    }

    public String scrollUntilFashionNews(String key) {

        scrollUntilElement(createPageSectionsMap().get(key));
        return getWebElementText(fashionNewsSectionTitle, "TITLE - FASHION NEWS SECTION");
    }


}
