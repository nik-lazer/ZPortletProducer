package lan.test.portlet.rmi.dto;

import java.io.Serializable;

/**
 * Dto for RMI
 * @author nik-lazer  31.07.2015   16:54
 */
public class BookDto implements Serializable {
	private String name;
	private String author;
	private Long isbnNumber;

	public BookDto() {
	}

	public BookDto(String name, String author, Long isbnNumber) {
		this.name = name;
		this.author = author;
		this.isbnNumber = isbnNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getIsbnNumber() {
		return isbnNumber;
	}

	public void setIsbnNumber(Long isbnNumber) {
		this.isbnNumber = isbnNumber;
	}
}
