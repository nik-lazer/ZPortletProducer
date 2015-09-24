package lan.test.portlet.simple;

import lan.test.portlet.zk.TestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Simple portlet with jsp page
 * @author nik-lazer 24.06.2013   16:13
 */
public class InitPortlet extends GenericPortlet {
	private Logger log = LoggerFactory.getLogger(InitPortlet.class);


	protected void doDispatch(RenderRequest request,
	                          RenderResponse response) throws PortletException,
	                                                          IOException {
		// Do nothing if window state is minimized.
		WindowState state = request.getWindowState();
		if (state.equals(WindowState.MINIMIZED)) {
			super.doDispatch(request, response);
			return;
		}

		// Get the content type for the response.
		String contentType = request.getResponseContentType();

		// Get the requested portlet mode.
		PortletMode mode = request.getPortletMode();

		// Reference a request dispatcher for dispatching to web resources
		PortletContext context = getPortletContext();
		PortletRequestDispatcher rd = null;

		// Dispatch based on content type and portlet mode.
		PortletURL portletURL = response.createActionURL();
		String s = portletURL.toString();
		rd = context.getRequestDispatcher("/view.jsp");
		if (rd != null) {
			rd.include(request, response);
		} else {
			super.doDispatch(request, response);
		}
		log.debug("Render Session ID: {}", request.getPortletSession(false).getId());
	}

	public void processAction(ActionRequest request, ActionResponse response) throws PortletException,IOException {
		String docTypeName = request.getParameter("name");

		QName qname = new QName("http://otr.ru/testevent" , "testEvent");

		TestEvent testEvent = new TestEvent();
		testEvent.setValue(docTypeName);
		response.setEvent(qname, testEvent);
	}

	public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
		log.debug("Resource Session ID: {}", request.getPortletSession(false).getId());
		URL res = getClass().getResource(request.getResourceID());
		InputStream resourceStream = getPortletContext().getResourceAsStream(request.getResourceID());
		copyStream(resourceStream, response.getPortletOutputStream());
	}

	public static void copyStream(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[1024]; // Adjust if you want
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1)
		{
			output.write(buffer, 0, bytesRead);
		}
	}
}
