package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import classesUtilities.Page;

public class SearchPage extends Page {
    private By itemsList = By.cssSelector("ul.product_list");

    public SearchPage(WebDriver driver){
        super(driver);
    }



//private List<HtmlElement> getSearchResultsList(By locator){
//     driver.findElements(locator);
//    return list;
//}


}