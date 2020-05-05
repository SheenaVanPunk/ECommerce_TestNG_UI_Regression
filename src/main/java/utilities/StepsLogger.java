package utilities;

import java.util.logging.Logger;

public class StepsLogger extends LoggingFileSetup {


    public StepsLogger() {}

//    public static final String LOG_STYLE	= "color : purple; font-weight: bold;";
//    public static final String DATA_STYLE 	= "color : magenta; font-weight: bold;";
//    public static final String VERIFY_STYLE	= "color : green; font-weight: bold;";
//    public static final String ERROR_STYLE 	= "color : red; font-weight: bold;";
//    public static final String INFO_STYLE 	= "color : blue; font-weight: bold;";

    public void step(String text) {
        System.out.println("STEP —" + " " + text.toString());
    }

    public void info(String text) {
        if (text.contains("\n")){
            System.out.println("----> INFO — " + text.replace("\n", " "));
        }else{
            System.out.println("-----> INFO — " + text);
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


//    private static String formatData(String text) {
//        return String.format(DATA_STYLE, text);
//    }
//
//    private static String formatInfo(String text) {
//        return String.format(INFO_STYLE, text);
//    }
//
//    private static String formatLog(String text) {
//        return String.format(LOG_STYLE, text);
//    }
//
//    private static String formatError(String text){
//        return String.format(ERROR_STYLE, text);
//    }
}

