package lan.test.portlet.rmi.service;

import lan.test.portlet.rmi.dto.BookDto;

import java.util.List;

/**
 * RMI interface
 * @author nik-lazer  31.07.2015   16:55
 */
public interface BookRMIService {
	List<BookDto> getBooks();
	BookDto getByISBN(Long isbn);
}
