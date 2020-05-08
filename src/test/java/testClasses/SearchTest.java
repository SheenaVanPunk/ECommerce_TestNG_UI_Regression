package testClasses;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.SearchPage;
import testUtilities.extentReportsClasses.ExtentReporterManager;
import testUtilities.extentReportsClasses.ExtentTestManager;

import java.lang.reflect.Method;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SearchTest extends BaseTest {

    @Test(groups = "regression")
    @Parameters("itemForSearch")
    public void testSearchForItem(String itemForSearch) {

        SearchPage sp = hp.searchForAnItem(itemForSearch);
        String searchPageTitle = sp.getPageTitle();
        assertFalse(searchPageTitle.contains(itemForSearch));
      }

}
