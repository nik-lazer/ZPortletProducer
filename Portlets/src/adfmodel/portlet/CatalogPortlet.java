package adfmodel.portlet;

import adfmodel.portlet.domain.Book;
import adfmodel.portlet.events.BookAddedEvent;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.ProcessAction;
import javax.portlet.RenderMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.xml.namespace.QName;
import java.io.IOException;

import java.util.Random;

/**
 * TODO: comment
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
		book.setIsbnNumber(random.nextLong());
		book.setName("Name");
		book.setPreferredBook(true);
		//QName eventName = new QName("http://www.mynamespace.com", "bookAddedEvent");
		//response.setEvent(eventName, new BookAddedEvent(book));
		//response.setEvent(eventName, "Event!!!");
		response.setRenderParameter("location", book.getIsbnNumber().toString());
	}

//	public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
//		super.processAction(request, response);
//	}
}
