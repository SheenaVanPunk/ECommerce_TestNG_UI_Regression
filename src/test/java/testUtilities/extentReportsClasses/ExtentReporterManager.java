package testUtilities.extentReportsClasses;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
<<<<<<< HEAD
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;


public class ExtentReporterManager {
=======
>>>>>>> working

public class ExtentReporterManager {
    private static ExtentReports extent;
<<<<<<< HEAD
    private static String reportFileName = "E-Commerce UI Regression Suite" + ".html";
    private static String fileSeperator = System.getProperty("file.separator");
    private static String reportFilepath = System.getProperty("user.home") + fileSeperator + fileSeperator+"TestReports";
    private static String reportFileLocation =  reportFilepath +fileSeperator+ reportFileName;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
=======
>>>>>>> working

    public static ExtentReports getInstance(){
        if(extent == null){
            var extent = createInstance();
        }
        return extent;
    }

    public static ExtentReports createInstance() {
<<<<<<< HEAD

        ExtentSparkReporter reporter = new ExtentSparkReporter(reportFilepath);

        reporter.config().setDocumentTitle("Extent Report"); //the page title
        reporter.config().setReportName(reportFileName); //the name of the report
        reporter.config().setEncoding("utf-8");
        reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        reporter.config().enableTimeline(true);
        reporter.config().setTheme(Theme.DARK);
=======
        String path = System.getProperty("user.home" + "\\TestReport\\report.html");
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setDocumentTitle("Test Report");
        reporter.config().setReportName("E-Commerce UI Regression Suite");
        reporter.config().setEncoding("utf-8");
>>>>>>> working

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        extent.setAnalysisStrategy(AnalysisStrategy.TEST);
        extent.setSystemInfo("Environment", "LAB");

        return extent;
    }
<<<<<<< HEAD

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
=======
>>>>>>> working
}
