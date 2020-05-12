package testUtilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.testng.annotations.DataProvider;

public class CsvParser {

    @DataProvider(name = "csvParser")
    public static Iterator<Object[]> getDataFromCsvFile(Method method) {
        List<Object[]> list = new ArrayList<Object[]>();
        String dataProviderPath = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "testData"
                + File.separator + method.getDeclaringClass().getSimpleName() + "_" + method.getName() + ".csv";
        File csvFile = new File(dataProviderPath);
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] keys = reader.readNext();
            int lenght = keys.length;
            if (keys != null) {
                String[] dataParts;

                while ((dataParts=reader.readNext()) != null) {
                    Map<String, String> testData = new HashMap<String, String>();
                    for (int i = 0; i < keys.length; i++) {
                        testData.put(keys[i], dataParts[i]);
                    }
                    list.add(new Object[]{testData});
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + dataProviderPath + " was not found.\n" + e.getStackTrace().toString());
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException("Could not read " + dataProviderPath + " file.\n" + e.getStackTrace().toString());
        }
        return list.iterator();
    }

}
