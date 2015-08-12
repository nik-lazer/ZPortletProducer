package lan.test.portlet.zk.wsrp;

import lan.test.portlet.zk.wsrp.session.DualSession;
import lan.test.portlet.zk.wsrp.session.DualSessionHelper;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Updating portlet session ID in http session
 * @author nik-lazer  11.08.2015   17:58
 */
public class LiferayDHtmlUpdateServlet extends DHtmlUpdateServlet {
	public void process(Session sess, HttpServletRequest request, HttpServletResponse response, boolean compress) throws ServletException, IOException {
		String portletSessionId = DualSessionHelper.getPortletSessionId(request);
		if (portletSessionId != null && (sess instanceof DualSession)) {
			((DualSession) sess).setPortletSessionId(portletSessionId);
		}
		super.process(sess, request, response, compress);
	}
}
