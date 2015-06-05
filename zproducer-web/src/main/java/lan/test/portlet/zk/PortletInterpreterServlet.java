package lan.test.portlet.zk;

import org.zkoss.web.servlet.dsp.InterpreterServlet;
import org.zkoss.zk.ui.WebApp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO: comment
 * @author nik-lazer  05.06.2015   09:40
 */
public class PortletInterpreterServlet extends InterpreterServlet {
	private static final String ATTR_INTERPRETER_SERVLET = "org.zkoss.web.servlet.dsp.InterpreterServlet";

	@Override
	public void init() throws ServletException {
		super.init();
		final ServletConfig config = getServletConfig();
		final ServletContext ctx = getServletContext();
		ctx.setAttribute(ATTR_INTERPRETER_SERVLET, this);
	}

	public static PortletInterpreterServlet getInterpreteServlet(WebApp webApp) {
		return (PortletInterpreterServlet) (webApp.getServletContext()).getAttribute(ATTR_INTERPRETER_SERVLET);
	}

	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
