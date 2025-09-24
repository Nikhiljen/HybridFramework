package com.org.utility;

import com.org.base.Base;
import com.org.utils.Excell_Utill;
import com.org.utils.TypeOfValidation;
import org.testng.annotations.DataProvider;

public class DataGetter extends Base {

    @DataProvider(name = "LoginData")
    public static Object[][] readDataFromLoginSheet(){
        String path = System.getProperty("user.dir") + "/test-data/testData.xlsx";
        return Excell_Utill.getTestData("loginData",path);
    }

    @DataProvider(name = "RegisterData")
    public static Object[][] readDataRegiterSheet(){
        String path = System.getProperty("user.dir") + "/test-data/testData.xlsx";
        Object[][] rawData = Excell_Utill.getTestData("RegisterData",path);

        // Convert last column (type) from String -> Enum
        for (int i = 0; i < rawData.length; i++) {
            String typeStr = rawData[i][6].toString().toUpperCase();
            rawData[i][6] = TypeOfValidation.valueOf(typeStr);
        }

        return rawData;
    }

    @DataProvider(name = "SearchItem")
    public static Object[][] readDataSearchItemSheet(){
        String path = System.getProperty("user.dir") + "/test-data/testData.xlsx";
        Object[][] rawData = Excell_Utill.getTestData("searchItem",path);

        // Convert last column (type) from String -> Enum
        for (int i = 0; i < rawData.length; i++) {
            String typeStr = rawData[i][1].toString().toUpperCase();
            rawData[i][1] = TypeOfValidation.valueOf(typeStr);
        }

        return rawData;
    }
}
