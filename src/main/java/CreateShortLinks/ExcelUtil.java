package CreateShortLinks;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

public class ExcelUtil {
    public static ArrayList<ArrayList<String>> getDataSheet(String fileName, String sheetName) throws IOException {
        ArrayList<ArrayList<String>> arrRow = new ArrayList<ArrayList<String>>();
        ArrayList<String> arrCol = null;

        FileInputStream file = null;
        file = new FileInputStream(new File(fileName));

        // Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        // Get first desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheet(sheetName);

        for (Row row : sheet) {
            arrCol = new ArrayList<String>();
            for (int cn = 0; cn < row.getLastCellNum(); cn++) {
                // If the cell is missing from the file, generate a blank
                // one
                // (Works by specifying a MissingCellPolicy)
                Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                // Print the cell for debugging
                switch (cell.getCellTypeEnum()) {
                    case NUMERIC: {
                        if (cell.getNumericCellValue() % 1 == 0) {
                            long value = new Double(cell.getNumericCellValue()).longValue();
                            arrCol.add(Long.toString(value));
                        } else
                            arrCol.add(String.valueOf(cell.getNumericCellValue()));
                        break;
                    }
                    case STRING: {
                        if (cell.getStringCellValue().equals("Field#1-error code")) {
                            arrCol.add("");
                        } else {
                            arrCol.add(cell.getStringCellValue().trim());
                        }
                        break;
                    }
                    case BOOLEAN: {
                        if (cell.getBooleanCellValue()) {
                            arrCol.add("true");
                        } else {
                            arrCol.add("false");
                        }
                        break;
                    }
                    case BLANK: {
                        arrCol.add("");
                        break;
                    }

                    default:
                        arrCol.add("default cell");
                        break;
                }
            }
            arrRow.add(arrCol);
        }
        file.close();
        workbook.close();
        return arrRow;
    }
    public static void writeResult (String fileName, String sheetName,int rowIndex , String shortlink) throws IOException{

        try {
            FileInputStream file = new FileInputStream(new File(fileName));

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Get first desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowIndex);
            row.createCell(3).setCellValue(shortlink);

            //
            FileOutputStream fos = new FileOutputStream(new File(fileName));
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();

        } catch (IOException e){
            e.printStackTrace();

        }
    }
}
