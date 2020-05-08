package testUtilities.extentReportsClasses;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.annotations.Parameters;

import java.io.File;


public class ExtentReporterManager {
    private static ExtentReports extent;

    private static String reportFileName = "E-Commerce UI Regression Suite" + ".html";
    private static String fileSeperator = System.getProperty("file.separator");
    private static String reportFilepath = System.getProperty("user.home") + fileSeperator + fileSeperator+"TestReports";
    private static String reportFileLocation =  reportFilepath +fileSeperator+ reportFileName;


    public static ExtentReports getInstance(){
        if(extent == null)
            createInstance();

        return extent;
    }
    @Parameters("browser")
    public static ExtentReports createInstance() {

        String path = System.getProperty("user.home" + "\\TestReport\\report.html");
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setDocumentTitle("Test Report");
        reporter.config().setReportName("E-Commerce UI Regression Suite");
        reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        reporter.config().setEncoding("utf-8");
        reporter.config().setTheme(Theme.DARK);


        extent = new ExtentReports();
        extent.attachReporter(reporter);

        extent.setAnalysisStrategy(AnalysisStrategy.TEST);
        extent.setSystemInfo("Environment", "LAB");
        extent.setSystemInfo("Browser", "browser");

        return extent;
    }

    //Create the report path
    private static String getReportPath (String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
                return reportFileLocation;
            } else {
                System.out.println("Failed to create directory: " + path);
                return System.getProperty("user.home");
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
        return reportFileLocation;
    }

}
