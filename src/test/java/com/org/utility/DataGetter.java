package com.org.utility;

import com.org.base.Base;
import com.org.utils.Excell_Utill;
import org.testng.annotations.DataProvider;

public class DataGetter extends Base {

    @DataProvider(name = "LoginData")
    public static Object[][] readData(){
        String path = System.getProperty("user.dir") + "/test-data/testData.xlsx";
        return Excell_Utill.getTestData("loginData",path);
    }
}
