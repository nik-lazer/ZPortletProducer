package lan.test.portlet.simple;

import lan.test.domain.Book;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

import java.util.Random;

/**
 * @author lazarev_nv 10.06.2013   12:14
 */
public class CatalogPortlet extends GenericPortlet {

	@RenderMode(name = "view")
	protected void view(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		doView(request, response);
	}

	protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException  {
		getPortletContext().getRequestDispatcher("/catalog/html/index.jsp").include(request, response);;
	}

	@ProcessAction(name = "addBookAction")
	public void addBook(ActionRequest request, ActionResponse response) throws PortletException, IOException {
		Book book = new Book();
		book.setAuthor("Author");
		book.setCategory("Category");
		Random random = new Random();
		book.setIsbnNumber(new Long(random.nextLong()).toString());
		book.setName("Name");
		book.setPreferredBook(true);
		//QName eventName = new QName("http://www.mynamespace.com", "bookAddedEvent");
		//response.setEvent(eventName, new BookAddedEvent(book));
		//response.setEvent(eventName, "Event!!!");
		response.setRenderParameter("location", book.getIsbnNumber());
	}

//	public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
//		super.processAction(request, response);
//	}
}
