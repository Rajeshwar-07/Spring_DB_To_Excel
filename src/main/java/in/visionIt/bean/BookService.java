package in.visionIt.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BookService {

	private BookDao bookDao;

	public BookService(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void generateExcel() {

		// create a workbook
		Workbook workbook = new XSSFWorkbook();

		// Create a sheet in the Workbook
		Sheet sheet = workbook.createSheet("Books");

		// create header row
		Row headerRow = sheet.createRow(0);
		Cell headerCell1 = headerRow.createCell(0);
		headerCell1.setCellValue("Book ID");

		Cell headerCell2 = headerRow.createCell(1);
		headerCell2.setCellValue("Book Name");

		Cell headerCell3 = headerRow.createCell(2);
		headerCell3.setCellValue("Price");

		// Fetch books from the database using bookDao
		List<Object[]> books = bookDao.getAllBooks();

		// write book data to the sheet
		int rowNum = 1;
		for (Object[] book : books) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue((Integer) book[0]);
			row.createCell(1).setCellValue((String) book[1]);
			row.createCell(2).setCellValue((Double) book[2]);
		}

		// Write a workbook to file
		try (FileOutputStream fileout = new FileOutputStream(new File("Books.xlsx"))) {
			workbook.write(fileout);
			System.out.println("Excel file generated successfully");
		} catch (IOException e) {
			System.out.println("Error while generating the Excel file : " + e.getMessage());
		}

		// close the workbook
		try {
			workbook.close();
		} catch (IOException e) {
			System.out.println("Error while closing the workbook : " + e.getMessage());
		}

	}

	public void storeBookData() throws Exception {
		File f = new File("Book.txt");

		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);

		String line = br.readLine();

		while (line != null) {
			String[] data = line.split(",");
			Integer bid = Integer.parseInt(data[0]);
			String name = data[1];
			Double price = Double.parseDouble(data[2]);

			bookDao.saveBook(bid, name, price);

			// read next line data
			line = br.readLine();
		}
		System.out.println("Book inserted in db table");
		br.close();
	}
}
