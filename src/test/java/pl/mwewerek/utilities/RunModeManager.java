package pl.mwewerek.utilities;

import pl.mwewerek.base.TestBase;

public class RunModeManager extends TestBase {

    public static boolean iSRunnable(String testName) {
        String sheetName = "testSuite";
        int rows = excel.getRowCount(sheetName);

        for (int rowNum = 2; rowNum <= rows; rowNum++) {
            String testCase = excel.getCellData(sheetName, "testCaseID", rowNum);
            if(testCase.equalsIgnoreCase(testName)) {
                String runMode = excel.getCellData(sheetName, "RunMode", rowNum);
                if (runMode.equalsIgnoreCase("Y")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    return false;
    }
}

