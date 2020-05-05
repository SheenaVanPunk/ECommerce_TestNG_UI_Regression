package utilities;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class LoggingFileSetup {

    private static String logFile = "C:/Users/Nena/log_folder/testLogs.txt";
    protected static PrintWriter writer;

    protected static void createLog() throws FileNotFoundException, UnsupportedEncodingException{
        writer = new PrintWriter(logFile, "UTF-8");
    }

    protected static void closeLog() throws FileNotFoundException, UnsupportedEncodingException{
        writer.close();
    }

    protected void writeToLog(String value) throws  FileNotFoundException, UnsupportedEncodingException{
        writer.println(value);
    }
}
