package lan.test.portlet.zk.wsrp.session;

import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.http.SimpleSession;

import javax.portlet.PortletSession;
import javax.servlet.http.HttpSession;

/**
 * Extension of {@link org.zkoss.zk.ui.http.SimpleSession} with joining with portlet session in Liferay portlet
 * @author nik-lazer  10.08.2015   14:33
 */
public class DualSimpleSession extends SimpleSession {
	private String portletSessionId;

	public DualSimpleSession(WebApp wapp, HttpSession hsess, Object request) {
		super(wapp, hsess, request);
	}

	public DualSimpleSession(WebApp wapp, Object navsess, Object request) {
		super(wapp, navsess, request);
	}

	public DualSimpleSession() {
	}

	public String getPortletSessionId() {
		return portletSessionId;
	}

	public void setPortletSessionId(String portletSessionId) {
		this.portletSessionId = portletSessionId;
	}

	public Object getAttribute(String name) {
		Object attr = super.getAttribute(name);
		PortletSession portletSession = getPortletSession();
		if ((attr == null) && (portletSession != null)) {
			attr = portletSession.getAttribute(name, PortletSession.APPLICATION_SCOPE);
		}
		return attr;
	}

	public Object setAttribute(String name, Object value) {
		Object ret = super.setAttribute(name, value);
		PortletSession portletSession = getPortletSession();
		if (portletSession != null) {
			portletSession.setAttribute(name, value, PortletSession.APPLICATION_SCOPE);
		}
		return ret;
	}

	public Object removeAttribute(String name) {
		Object ret = super.removeAttribute(name);
		PortletSession portletSession = getPortletSession();
		if (portletSession != null) {
			portletSession.removeAttribute(name, PortletSession.APPLICATION_SCOPE);
		}
		return ret;
	}

	private PortletSession getPortletSession() {
		PortletSession portletSession = null;
		if (portletSessionId != null) {
			portletSession = DualSessionHelper.getPortletSession(portletSessionId);
		}
		return portletSession;
	}
}
