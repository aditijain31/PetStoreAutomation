package api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {

	String path;

	public XLUtility(String path) {
		this.path = path;
	}

	public int getRowCount(String sheetName) {
		int rowCount = 0;
		try (FileInputStream fileInputStream = new FileInputStream(path);
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet != null) {
				rowCount = sheet.getLastRowNum();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	public int getCellCount(String sheetName, int rowNum) {
		int cellCount = 0;
		try (FileInputStream fileInputStream = new FileInputStream(path);
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet != null) {
				XSSFRow row = sheet.getRow(rowNum);
				if (row != null) {
					cellCount = row.getLastCellNum();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cellCount;
	}

	public String getCellData(String sheetName, int rowNum, int cellNum) {
		String cellData = "";

		try (FileInputStream fileInputStream = new FileInputStream(path);
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);

			if (sheet != null) {
				XSSFRow row = sheet.getRow(rowNum);
				if (row != null) {
					XSSFCell cell = row.getCell(cellNum);
					DataFormatter dataFormatter = new DataFormatter();
					cellData = dataFormatter.formatCellValue(cell);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cellData;
	}

	public void setCellData(String sheetName, int rowNum, int cellNum, String data) {

		try (FileInputStream fileInputStream = new FileInputStream(path);
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {

			XSSFSheet sheet;

			if (workbook.getSheetIndex(sheetName) == -1) {
				sheet = workbook.createSheet(sheetName);
			} else {
				sheet = workbook.getSheet(sheetName);
			}
			XSSFRow row = sheet.getRow(rowNum);
			if (row == null) {
				row = sheet.createRow(rowNum);
			}

			XSSFCell cell = row.createCell(cellNum);
			cell.setCellValue(data);

		} catch (IOException e) {
			e.printStackTrace();
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(path);
				XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(path))) {
			workbook.write(fileOutputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
