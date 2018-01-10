package ReadQRCode;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Utils {
    public ArrayList<String> getImagesPath(String folderPath) {
        ArrayList<String> listImage = new ArrayList<String>();
        File dir = new File(folderPath);
        File[] listFile = dir.listFiles();
        for (File file : listFile) {
            if (file.isFile()) {
                String fileExtension = this.getFileExtension(file.getName()).toLowerCase();
                if (fileExtension.endsWith("jpg")||fileExtension.endsWith("jpeg") || fileExtension.endsWith("png")){
                    listImage.add(file.getAbsolutePath());
                }
            }
        }
        return listImage;
    }

    public String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".")+1);
        }
        return "";
    }
    public void writeResult(Sheet sheet) throws IOException {
        ArrayList<String> listImage = this.getImagesPath(System.getProperty("user.dir") + File.separator + "QRCodeImage");
        CellStyle hlinkStyle = sheet.getWorkbook().createCellStyle();
        Font hlinkFont = sheet.getWorkbook().createFont();
        hlinkFont.setUnderline(XSSFFont.U_SINGLE);
        hlinkFont.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
        hlinkStyle.setFont(hlinkFont);
        for (int i=1;i <listImage.size() +1;i++){
        try {
            Row row = sheet.createRow(i);
            Cell fileName = row.createCell(0);
            fileName.setCellValue(listImage.get(i-1));
            fileName.setCellStyle(hlinkStyle);
            Cell shortLink = row.createCell(1);
            shortLink.setCellValue(ReadQRCode.readQRCode(listImage.get(i-1)));
            shortLink.setCellStyle(hlinkStyle);

        } catch (Exception e) {

        }

        }

    }
    public void createFileReport(){
        String currentDir = System.getProperty("user.dir") + File.separator;
        File file = new File(currentDir +"Result.xlsx");
        FileInputStream fileInputStream = null;
        XSSFWorkbook workbook = null;
        try {
            if (file.exists()){
               file.delete();
            }
            file.createNewFile();
            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Result");
            this.createHeaderRow(sheet);
            this.writeResult(sheet);
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            FileOutputStream outputStream = new FileOutputStream(currentDir + "Result.xlsx");
            workbook.write(outputStream);

        } catch (FileNotFoundException e){

        } catch (IOException e){

        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void createHeaderRow(Sheet sheet){
        try {
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderRight(BorderStyle.DASH_DOT);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            Row row = sheet.createRow(0);
            Cell fileNameRow = row.createCell(0);
            fileNameRow.setCellValue("FileName");
            fileNameRow.setCellStyle(cellStyle);
            Cell originalLink = row.createCell(1);
            originalLink.setCellValue("OriginalLink");
            originalLink.setCellStyle(cellStyle);
            Cell alias = row.createCell(2);
            alias.setCellValue("Alias");
            alias.setCellStyle(cellStyle);
            Cell shortlink = row.createCell(3);
            shortlink.setCellValue("Shortlink");
            shortlink.setCellStyle(cellStyle);


        } catch (Exception e){
            e.getStackTrace();
        }
    }

}
