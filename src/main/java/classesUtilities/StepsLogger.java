package classesUtilities;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class StepsLogger {

    public StepsLogger() {}


    public void step(String text) {
        if (text.contains("\n")){
            System.out.println("STEP — " + text.replace("\n", " "));


        }else{
            System.out.println("STEP — " + text);

        }

    }

    public void info(String text) {
        if (text.contains("\n")){
            System.out.println("-> INFO — " + text.replace("\n", " "));

        }else{
            System.out.println("-> INFO — " + text);

        }
    }

    public void error(String text) {
        System.out.println(" ! ERROR — " + text);
    }

    public void failed(String text){
        System.out.println("FAILED - " + text);
    }

    public void passed(String text){
        System.out.println("PASSED - " + text);
    }


}

