package Utility.com;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Library {

	// Method to read data from an Excel sheet
	public static List<List<String>> readExcel(String excelPath, String sheetName)
			throws EncryptedDocumentException, IOException {
		List<List<String>> data = new ArrayList<>();
		FileInputStream file = new FileInputStream(excelPath);
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheet(sheetName);
		for (Row row : sheet) {
			List<String> rowData = new ArrayList<>();
			for (Cell cell : row) {
				rowData.add(cell.toString());
			}
			data.add(rowData);
		}
		workbook.close();
		return data;
	}

	// Method to write data to an Excel sheet in multiple columns
	public static void writeDataToExcel(String excelPath, List<WebElement> options) throws IOException {
		// Create Excel file
		FileOutputStream file = new FileOutputStream(excelPath);
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sheet1");

		// Create header row
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Station Name");

		
		// Populate Excel sheet with options in multiple columns
		for (int i = 0; i < options.size(); i++) {
			Row dataRow = sheet.getRow(i + 1);
			if (dataRow == null) {
				dataRow = sheet.createRow(i + 1);
			}
			dataRow.createCell(i).setCellValue(options.get(i).getText());
		}

		workbook.write(file);
		file.close();
		workbook.close();
	}

	public static void handleCalenederDynamically(WebDriver driver, WebElement ele) {
		try {
			LocalDate currentDate = LocalDate.now();
			System.out.println("current Date: "+currentDate);
			LocalDate targetDate = currentDate.plusDays(30);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String formattedDate = targetDate.format(formatter);

			// Set the target date using JavaScript Executor
			String script = "arguments[0].setAttribute('value', '" + formattedDate + "')";
			((JavascriptExecutor) driver).executeScript(script, ele);

			// Verify the date has been set
			String selectedDate = ele.getAttribute("value");
			System.out.println("Selected Date: " + selectedDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
