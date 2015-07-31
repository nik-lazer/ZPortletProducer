package lan.test.portlet.rmi.service;

import lan.test.portlet.rmi.dto.BookDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link lan.test.portlet.rmi.service.BookRMIService}
 * @author nik-lazer  31.07.2015   17:05
 */
public class BookRMIServiceImpl implements BookRMIService {
	private Map<Long, BookDto> books = new HashMap();

	public BookRMIServiceImpl() {
		BookDto bookDto = new BookDto("Spring in Action, Third Edition", "Craig Walls", 9781935182351L);
		books.put(bookDto.getIsbnNumber(), bookDto);
		bookDto = new BookDto("Hibernate in Action", "Christian Bauer and Gavin King", 9781932394153L);
		books.put(bookDto.getIsbnNumber(), bookDto);
		bookDto = new BookDto("jQuery in Action, Third Edition", "Bear Bibeault, Yehuda Katz, and Aurelio De Rosa\n", 9781617292071L);
		books.put(bookDto.getIsbnNumber(), bookDto);
	}

	@Override
	public List<BookDto> getBooks() {
		return new ArrayList<>(books.values());
	}

	@Override
	public BookDto getByISBN(Long isbn) {
		return books.get(isbn);
	}
}
