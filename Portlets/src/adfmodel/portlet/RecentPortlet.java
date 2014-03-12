package adfmodel.portlet;

import adfmodel.portlet.events.BookAddedEvent;

import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import java.io.IOException;

/**
 * TODO: comment
 * @author lazarev_nv 10.06.2013   12:14
 */
public class RecentPortlet extends GenericPortlet {
	@RenderMode(name = "view")
	protected void view(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		doView(request, response);
	}

	protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException  {
	    String isbnNumber = request.getParameter("location");
	    request.setAttribute("location", "location = "+isbnNumber);
		getPortletContext().getRequestDispatcher("/recent/html/index.jsp").include(request, response);
	}


//	@ProcessEvent(qname="{http://www.mynamespace.com}bookAddedEvent")
//	public void processAddedBookEvent(EventRequest request, EventResponse eventResponse)
//			throws IOException, PortletException {
//		Event event = request.getEvent();
//		String ee = (String)event.getValue();
//		eventResponse.setRenderParameter("category", ee);
//		BookAddedEvent bookAddedEvent = (BookAddedEvent)event.getValue();
//		eventResponse.setRenderParameter("category", bookAddedEvent.getCategory());
//		eventResponse.setRenderParameter("name", bookAddedEvent.getName());
//		eventResponse.setRenderParameter("author", bookAddedEvent.getAuthor());
//		eventResponse.setRenderParameter("isbnNumber", String.valueOf(bookAddedEvent.getIsbnNumber()));
//	}

	/*public void processEvent(EventRequest request, EventResponse response)
			throws PortletException, java.io.IOException {
		super.processEvent(request, response);
	}  */

}
