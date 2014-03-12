package adfmodel.portlet.events;

import adfmodel.portlet.domain.Book;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * TODO: comment
 * @author lazarev_nv 10.06.2013   15:27
 */
@XmlRootElement
public class BookAddedEvent implements Serializable {
	private String name;
	private String author;
	private Long isbnNumber;
	private String category;

	public BookAddedEvent() {
		//-- do nothing
	}

	public BookAddedEvent(Book book) {
		this.name = book.getName();
		this.author = book.getAuthor();
		this.category = book.getCategory();
		this.isbnNumber = book.getIsbnNumber();
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}