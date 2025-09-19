package com.org.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class Excell_Utill {

    public static  Object[][] getTestData(String SheetName, String filepath) {
        Object[][] data = null;

        try (FileInputStream fis = new FileInputStream(filepath);
             Workbook workbook = new XSSFWorkbook(fis))
        {
             Sheet sheet = workbook.getSheet(SheetName);
             int rows = sheet.getPhysicalNumberOfRows();
             int cols = sheet.getRow(0).getLastCellNum();

             data = new Object[rows-1][cols];

            for(int i = 1; i < rows; i++){
                Row row = sheet.getRow(i);
                for(int j =0;j < cols; j++ ){
                    Cell cell = row.getCell(j);
                    data[i-1][j] = getCellValue(cell);
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }

    private static Object getCellValue(Cell cell) {
        if(cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case BOOLEAN -> cell.getBooleanCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };

    }
}
