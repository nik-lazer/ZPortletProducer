package lan.test.portlet.zk.wsrp.session;

import lan.test.config.ApplicationContextProvider;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.impl.AbstractUiFactory;

import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Factory for {@link lan.test.portlet.zk.wsrp.session.DualSerializableSession}. It sets portlet session identifier into
 * {@link lan.test.portlet.zk.wsrp.session.DualSerializableSession} if this identifier exists
 * @author nik-lazer  10.08.2015   14:48
 */
public class DualSerializableUiFactory extends AbstractUiFactory {
	public Session newSession(WebApp wapp, Object nativeSess, Object request) {
		if (nativeSess instanceof HttpSession) {
			DualSerializableSession session =  new DualSerializableSession(wapp, (HttpSession) nativeSess, request);
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String portletSessionId = DualSessionHelper.getPortletSessionId(httpServletRequest);
			if (portletSessionId != null) {
				session.setPortletSessionId(portletSessionId);;
			}
			return session;
		} else {
			return new DualSerializableSession(wapp, (PortletSession) nativeSess, request);
		}
	}
}
