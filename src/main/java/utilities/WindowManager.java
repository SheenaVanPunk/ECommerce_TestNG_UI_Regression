package utilities;


import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class WindowManager extends Page {

    public WindowManager(WebDriver driver) {
        super(driver);
    }

    public void goBack() {
        driver.navigate().back();
        new StepsLogger().info("Returned to the previous page " + "\""+ driver.getTitle() + "\"");
    }

    public void goForward() {
        driver.navigate().forward();
        new StepsLogger().info("Moved forward to page " + "\""+ driver.getTitle() + "\"");
    }

    public void refreshPage() {
        driver.navigate().refresh();
        new StepsLogger().info("Refreshed the page.");
    }

    public void goToUrl(String url) {
        driver.navigate().to(url);
        new StepsLogger().info("Navigated to URL " + url);
    }

    public void switchToTabByTitle(String tabTitle) {
        var tabs = driver.getWindowHandles();

        for (String tab : tabs) {
            driver.switchTo().window(tab);
            if (tabTitle.equals(driver.getTitle())) {
                break;
            }
        }
        new StepsLogger().info("Switched to " + "\""+ driver.getTitle() + "\"" + " tab by page title.");
    }

    public void switchToTabByHandle(String handle) {
        var tabs = driver.getWindowHandles();

        for (String tab : tabs) {
            driver.switchTo().window(tab);
            if (handle.equals(getTabHandle())) {
                break;
            }
        }
        new StepsLogger().info("Switched to " + "\""+ driver.getTitle() + "\"" + " tab by window handle.");
    }

    public void switchToTabByIndex(int tab) {
        ArrayList tabs = new ArrayList(driver.getWindowHandles());
        waitForAllTabsToLoad();

        for (int i = 1; i < tabs.size(); i++) {
            driver.switchTo().window((tabs.get(i)).toString());
            if (i == tab) {
                break;
            }
        }
        new StepsLogger().info("Switched to " + "\""+ driver.getTitle() + "\"" + " tab by index.");
    }

    public void switchToTheFirstOpenedTab() {
        var tabs = driver.getWindowHandles();
        System.out.println(tabs);
        tabs.forEach(driver.switchTo()::window);
        new StepsLogger().info("Switched to the next tab.");
    }

    public int getNumberOfOpenTabs() {
        int openTabs = driver.getWindowHandles().size();
        new StepsLogger().info("The number of open tabs at the moment: " + openTabs);
        return openTabs;
    }

    public void openANewTab(){

    }


    public void closeAllAddedTabs() {
        ArrayList tabs = new ArrayList(driver.getWindowHandles());

        if (tabs.size() > 1) {
            for (int i = 1; i < tabs.size(); i++) {
                driver.switchTo().window(tabs.get(i).toString());
                driver.close();
            }
        }
        new StepsLogger().info("Closed all open tabs.");
    }


}
