package lan.test.portlet.rmi.main;

import lan.test.portlet.rmi.service.BookRMIService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main for rmi server
 * @author nik-lazer  31.07.2015   17:08
 */
public class RmiServer {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("rmiClientContext.xml");
		BookRMIService proxyService = (BookRMIService) applicationContext.getBean("bookService");
	}
}
