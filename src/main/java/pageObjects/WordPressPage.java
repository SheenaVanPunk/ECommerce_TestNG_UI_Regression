package pageObjects;

import classesUtilities.Page;
import org.openqa.selenium.WebDriver;

public class WordPressPage extends Page {
    private static String pageTitle = "Log In ‹ ToolsQA Demo Site — WordPress";

    public WordPressPage(WebDriver driver){
        super(driver);
    }

    public String getExpectedPageTitle(){
        return pageTitle;
    }

    public String getActualPageTitle(){
        return getPageTitle();
    }

}
