package CreateShortLinks;

        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayList;

public class Excelbuilder {
    public ArrayList<Shortlink> createDataTest(String excelFile, String sheet) {
        // Get data from excel file
        String excelPath = System.getProperty("user.dir") + File.separator + excelFile;
        ArrayList<ArrayList<String>> rows = null;
        try {
            rows = ExcelUtil.getDataSheet(excelPath, sheet);
        } catch (IOException e1) {
        }
        ArrayList<Shortlink> data = new ArrayList<Shortlink>();


        // Loop for get data from row 4 -> Count - 1
        for (int rowNum = 1; rowNum < rows.size(); rowNum++) {
            Shortlink shortlink = new Shortlink();
            shortlink.setOriginalLink(rows.get(rowNum).get(1));
            shortlink.setAlias(rows.get(rowNum).get(2));
            System.out.println(shortlink.getOriginalLink());
            System.out.println(shortlink.getAlias());
            data.add(shortlink);
        }
        return data;
    }
}
