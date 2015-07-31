package lan.test.rmi;

import lan.test.portlet.rmi.service.BookRMIService;

/**
 * Bean for {@link lan.test.portlet.rmi.service.BookRMIService} calling
 * @author nik-lazer  31.07.2015   17:31
 */
public class BookServiceProxyBean {
	private BookRMIService bookRMIService;

	public BookRMIService getBookRMIService() {
		return bookRMIService;
	}

	public void setBookRMIService(BookRMIService bookRMIService) {
		this.bookRMIService = bookRMIService;
	}
}
