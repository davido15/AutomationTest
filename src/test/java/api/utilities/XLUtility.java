package api.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLUtility {

    private String filePath;

    public XLUtility(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Get the total number of cells in a row.
     *
     * @param sheetName the name of the sheet
     * @param rowIndex  the row index (0-based)
     * @return the number of cells in the row
     * @throws IOException if an error occurs while reading the file
     */
    public int getCellCount(String sheetName, int rowIndex) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist.");
            }

            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                return 0;
            }

            return row.getPhysicalNumberOfCells();
        }
    }

    /**
     * Get the data from a specific cell.
     *
     * @param sheetName the name of the sheet
     * @param rowIndex  the row index (0-based)
     * @param colIndex  the column index (0-based)
     * @return the data from the cell as a String
     * @throws IOException if an error occurs while reading the file
     */
    public String getCellData(String sheetName, int rowIndex, int colIndex) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist.");
            }

            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                return "";
            }

            Cell cell = row.getCell(colIndex);
            if (cell == null) {
                return "";
            }

            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    } else {
                        return String.valueOf(cell.getNumericCellValue());
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case BLANK:
                    return "";
                default:
                    return "Unsupported cell type";
            }
        }
    }

    /**
     * Sets data in a specific cell.
     *
     * @param sheetName the name of the sheet
     * @param rowIndex  the row index (0-based)
     * @param colIndex  the column index (0-based)
     * @param data      the data to set in the cell
     * @throws IOException if an error occurs while reading/writing the file
     */
    public void setCellData(String sheetName, int rowIndex, int colIndex, String data) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist.");
            }

            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }

            Cell cell = row.getCell(colIndex);
            if (cell == null) {
                cell = row.createCell(colIndex);
            }

            cell.setCellValue(data);

            // Write changes back to the file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
    }
}
