package ReadQRCode;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

public class Utils {
    public ArrayList<String> imageFilePath(String folderPath) {
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
    public void writeReport() throws IOException {

    }
    public void createFileReport(){
        String currentDir = System.getProperty("user.dir") + File.separator;
        File file = new File(currentDir +"Result.xlsx");
        FileInputStream fileInputStream = null;
        XSSFWorkbook workbook = null;
        try {
            if (file.exists()){
                fileInputStream = new FileInputStream(currentDir + "Result.xlsx");
                POIFSFileSystem fsPoi = new POIFSFileSystem(fileInputStream);
            } else {
                file.createNewFile();
                workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Result");
            }
            FileOutputStream outputStream = new FileOutputStream(currentDir + "Result.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e){

        } catch (IOException e){

        }


    }

}
