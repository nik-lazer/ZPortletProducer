package lan.test.domain;

/**
 * Entity for samples
 * @author lazarev_nv 10.06.2013   15:28
 */
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="book")
public class Book {
	private String name;
	private String author;
	private String isbnNumber;
	private String category;
	private boolean preferredBook;

	public Book(String category, String name, String author, String isbnNumber) {
		this.category = category;
		this.name = name;
		this.author = author;
		this.isbnNumber = isbnNumber;
	}
	public boolean isPreferredBook() {
		return preferredBook;
	}
	public void setPreferredBook(boolean isPreferredBook) {
		this.preferredBook = isPreferredBook;
	}
	public Book() {
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

	public String getIsbnNumber() {
		return isbnNumber;
	}

	public void setIsbnNumber(String isbnNumber) {
		this.isbnNumber = isbnNumber;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public boolean equals(Object otherObject) {
		Book otherBook = (Book)otherObject;
		if ((isbnNumber == null) || (otherBook.isbnNumber == null)) {
			return false;
		}
		if(otherBook.getIsbnNumber().equals(this.isbnNumber)) {
			return true;
		} else {
			return false;
		}
	}
}
