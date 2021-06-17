package pl.mwewerek.utilities;

import pl.mwewerek.base.TestBase;

import java.lang.reflect.Method;
import java.util.Hashtable;

public class DataProvider extends TestBase {

    @org.testng.annotations.DataProvider(name = "dataProvider")
    public static Object[][] getData(Method method) {  // Method method get method name

        String sheetName = method.getName();  // Returns the name of the method
        int rows = excel.getRowCount(sheetName);
        int columns = excel.getColumnCount(sheetName);
        Object[][] data = new Object[rows - 1][1];  // remember that first row is not counting, so we make - 1
        // only one column 'cause we use Hashtable collection here
        // rows start counting from 1 - columns start counting from 0 !!!
        for (int rowNum = 2; rowNum <= rows; rowNum++) {  // we start from second row here!!!
            Hashtable<String, String> table = new Hashtable<>(); // We create Hashtable for every row
            for (int colNum = 0; colNum < columns; colNum++) {
                table.put(excel.getCellData(sheetName, colNum, 1), // key
                        excel.getCellData(sheetName, colNum, rowNum));  // value
            }
            data[rowNum - 2][0] = table;
        }
        return data;
    }
}
