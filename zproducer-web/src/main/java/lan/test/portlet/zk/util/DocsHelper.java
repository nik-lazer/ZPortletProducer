package lan.test.portlet.zk.util;

import lan.test.domain.Book;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates files for download
 * @author nik-lazer  29.10.2015   16:26
 */
public class DocsHelper {
	public static byte[] createXls() {
		List<Book> books = getBooks();
		// Using XSSF for xlsx format, for xls use HSSF
		Workbook workbook = new HSSFWorkbook();
		Sheet studentsSheet = workbook.createSheet("Books");
		int rowIndex = 0;
		for(Book book : books){
			Row row = studentsSheet.createRow(rowIndex++);
			int cellIndex = 0;
			row.createCell(cellIndex++).setCellValue(book.getName());
			row.createCell(cellIndex++).setCellValue(book.getAuthor());
			row.createCell(cellIndex++).setCellValue(book.getCategory());
			row.createCell(cellIndex++).setCellValue(book.getIsbnNumber());
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			workbook.write(byteArrayOutputStream);
			byteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArrayOutputStream.toByteArray();
	}

	private static List<Book> getBooks() {
		List<Book> books = new ArrayList<>();
		Book book = new Book("Detective", "Ten Little Niggers", "Agatha Christie", "978-0-00-713683-4");
		books.add(book);
		book = new Book("Fiction", "Fahrenheit 451", "Ray Bradbury", "978-5-699-59370-5");
		books.add(book);
		return books;
	}
}
