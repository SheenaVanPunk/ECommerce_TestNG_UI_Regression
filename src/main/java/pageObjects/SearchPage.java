package pageObjects;

import org.openqa.selenium.By;
import utilities.Page;

public class SearchPage extends Page {
    private By itemsList = By.cssSelector("ul.product_list");

    public SearchPage(){
        super(driver);
    }



//private List<HtmlElement> getSearchResultsList(By locator){
//     driver.findElements(locator);
//    return list;
//}


}