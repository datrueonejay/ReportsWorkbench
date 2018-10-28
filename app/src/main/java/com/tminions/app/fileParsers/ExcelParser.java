package com.tminions.app.fileParsers;

import com.tminions.app.Normalizer.InvalidFormatException;
import com.tminions.app.Normalizer.Normalizer;
import com.tminions.app.clientRecord.ClientRecord;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class ExcelParser {

    /**
     * 0-based index of row where columns names are in excelFile
     */
    static final int COLUMN_HEADS_ROW = 1;

    /**
     * 0-based index of row where columns names are in excelFile
     */
    static final int VISIBLE_COLUMN_HEADS_ROW = 2;

    /**
     * 0-based index of row where the clients records start in excelFile
     */
    static final int CLIENT_START_ROW = 3;

    /**
     * we will assume every file has one sheet
     */
    static final int DEFAULT_SHEET_INDEX = 0;

    private File excelFile;
    private List<String> columnHeaders;

    private List<String> visibleColumnHeaders;


    public Workbook getWb() {
        return wb;
    }

    public void setWb(Workbook wb) {
        this.wb = wb;
    }

    private Workbook wb;

    public List<String> getColumnHeaders() {
        return columnHeaders;
    }

    public void setColumnHeaders(List<String> columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public File getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(File excelFile) {
        this.excelFile = excelFile;
    }

    /**
     * initializes new excelParser and its workbook
     * @param file The excel file
     * @throws IOException if problem creating FileInputStream
     * from file
     */
    public ExcelParser(File file) throws IOException{
        this.excelFile = file;

        FileInputStream fis = new FileInputStream(this.excelFile);

        if (excelFile.getName().toLowerCase().endsWith("xlsx")) {
            this.wb = new XSSFWorkbook(fis);
        } else if (excelFile.getName().toLowerCase().endsWith("xls")) {
            this.wb = new HSSFWorkbook(fis);
        }
        fis.close();

        this.columnHeaders = new ArrayList<>();
    }

    /**
     * reads the clients from one excel file, assuming it has one sheet and the sheet is a template
     * @return ArrayList of ClientRecords from the sheet
     * @throws IOException if problem reading file
     */
    public ArrayList<ClientRecord> parseClients() throws IOException{

        Sheet sheet = this.wb.getSheetAt(0);

        this.setColumnHeaders(sheet);

        ArrayList<ClientRecord> clients = readClients(sheet);

        return clients;

    }

    /**
     * sets this.columnHeaders based on the column headers sheet
     * @param sheet the sheet
     */
    public void setColumnHeaders(Sheet sheet) throws IOException{

        Row headersRow = sheet.getRow(COLUMN_HEADS_ROW);

        Row visibleHeadersRow = sheet.getRow(VISIBLE_COLUMN_HEADS_ROW)

        Iterator<Cell> iter = headersRow.cellIterator();

        while (iter.hasNext()) {
            Cell cell = iter.next();
            if (cell.getCellType() != CellType.STRING) {
                throw new IOException("All column headers in excel file must be Strings");
            } else {
                this.columnHeaders.add(cell.getStringCellValue());
                this.visibleColumnHeaders.add(visibleHeadersRow
                        .getCell(cell.getColumnIndex()).getStringCellValue());
            }
        }
    }

    /**
     * returns an ArrayList of ClientRecord objects from sheet starting at rowIndex and using colHeaders as keys
     * @param sheet sheet with clients
     * @return ArrayList of ClientRecord representing the clients in sheet
     */
    public ArrayList<ClientRecord> readClients(Sheet sheet) {

        ArrayList<ClientRecord> clients = new ArrayList<ClientRecord>();

        // get to the rowIndex-th row
        Iterator<Row> rowIter = sheet.rowIterator();
        for (int i = 0; i < CLIENT_START_ROW ; i++) {
            rowIter.next();
        }

        // Go through each row, build a ClientRecord and add to clients
        while (rowIter.hasNext()) {

            Row row = rowIter.next();

            /*
             determine if the row is empty by checking if the first 5 cells are blank
             because sheet.lastRowNum() isn't trustable since some columns are dropdowns
             until row #2503 (ie. in example icare template).
             */

            boolean empty = true;
            int i = 0;
            while (empty && i < 5) {
                if (row.getCell(i).getCellType() != CellType.BLANK){
                    empty = false;
                }
                i++;
            }

            if (empty)
                break;

            // build a client with the data in that row's cells
            ClientRecord client = new ClientRecord();
            Iterator<Cell> cellIterator = row.cellIterator();
            int col = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                // only add a key-value if the value exists (aka cell isn't blank)
                if (cell.getCellType() != CellType.BLANK){

                    // check if it passes Normalizer
                    try {
                        String data = Normalizer.verify(cell.getStringCellValue(), this.visibleColumnHeaders.get(col));
                        client.put(this.columnHeaders.get(col), data);

                    } catch (InvalidFormatException e) {
                        client.putInvalid(this.columnHeaders.get(col), cell.getStringCellValue());
                    }

                }
                col++;
            }

            clients.add(client);
        }

        return clients;


    }

}
