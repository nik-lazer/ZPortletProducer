package lan.test.portlet.zk.wsrp.session;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.impl.AbstractUiFactory;

import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Factory for {@link lan.test.portlet.zk.wsrp.session.DualSimpleSession}. It sets portlet session identifier into
 * {@link lan.test.portlet.zk.wsrp.session.DualSimpleSession} if this identifier exists
 * @author nik-lazer  10.08.2015   14:49
 */
public class DualSimpleUiFactory extends AbstractUiFactory {
	public Session newSession(WebApp wapp, Object nativeSess, Object request) {
		if (nativeSess instanceof HttpSession) {
			DualSimpleSession session = new DualSimpleSession(wapp, (HttpSession) nativeSess, request);
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String portletSessionId = DualSessionHelper.getPortletSessionId(httpServletRequest);
			if (portletSessionId != null) {
				session.setPortletSessionId(portletSessionId);;
			}
			return session;
		} else {
			return new DualSimpleSession(wapp, (PortletSession) nativeSess, request);
		}
	}
}
