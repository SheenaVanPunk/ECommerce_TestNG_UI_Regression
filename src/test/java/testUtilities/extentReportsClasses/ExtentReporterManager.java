package testUtilities.extentReportsClasses;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import net.bytebuddy.asm.Advice;
import org.testng.ITestResult;
import testClasses.QuickSmokeTest;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;


public class ExtentReporterManager {
    private static ExtentReports extent;
    private static ITestResult result;
    private static String relativePathToReportFolder = "\\resources\\failedTestScreenshots\\" + LocalDate.now() +
            "\\QuickSmokeTest\\";

    public static ExtentReports getInstance(){
        if(extent == null) {
            createInstance();
        }
        return extent;
    }

    public static ExtentReports createInstance() {
        String fileName = getReportName();
        String directory = System.getProperty("user.dir") + relativePathToReportFolder;

        new File(directory).mkdirs();
        String pathToReport = directory + fileName;
        ExtentSparkReporter reporter = new ExtentSparkReporter(pathToReport);
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

    private static String getReportName() {
        LocalDate d = LocalDate.now();
        return "Test Report" + "_"
                        + d.toString()
                        .replace(" ", "_") + ".html";
    }


    //Create the report path
//    private static String getReportPath (String path) {
//        File testDirectory = new File(path);
//        if (!testDirectory.exists()) {
//            if (testDirectory.mkdir()) {
//                System.out.println("Directory: " + path + " is created!" );
//                return reportFileLocation;
//            } else {
//                System.out.println("Failed to create directory: " + path);
//                return System.getProperty("user.home");
//            }
//        } else {
//            System.out.println("Directory already exists: " + path);
//        }
//        return reportFileLocation;
//    }

}
