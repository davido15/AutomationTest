package api.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    private String filePath;

    public DataProvider(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads all data from the given sheet.
     *
     * @param sheetName the name of the sheet
     * @return a list of lists containing all data in the sheet
     * @throws IOException if an error occurs while reading the file
     */
    public List<List<String>> getAllData(String sheetName) throws IOException {
        List<List<String>> sheetData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist.");
            }

            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            rowData.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                rowData.add(cell.getDateCellValue().toString());
                            } else {
                                rowData.add(String.valueOf(cell.getNumericCellValue()));
                            }
                            break;
                        case BOOLEAN:
                            rowData.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case BLANK:
                            rowData.add("");
                            break;
                        default:
                            rowData.add("Unsupported cell type");
                    }
                }
                sheetData.add(rowData);
            }
        }

        return sheetData;
    }

  
    
    public List<String> getOnlyUsernames(String sheetName) throws IOException {
        List<String> usernames = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist.");
            }

            for (Row row : sheet) {
                Cell cell = row.getCell(1); // Assuming usernames are in the second column
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    usernames.add(cell.getStringCellValue());
                }
            }
        }

        return usernames;
    }
}
