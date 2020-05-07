package testUtilities.extentReportsClasses;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance(){
        if(extent == null){
            var extent = createInstance();
        }
        return extent;
    }

    public static ExtentReports createInstance() {
        String path = System.getProperty("user.home" + "\\TestReport\\report.html");
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setDocumentTitle("Test Report");
        reporter.config().setReportName("E-Commerce UI Regression Suite");
        reporter.config().setEncoding("utf-8");

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        return extent;
    }
}
