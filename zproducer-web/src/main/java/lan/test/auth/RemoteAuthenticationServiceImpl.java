package lan.test.auth;

import javax.portlet.PortletRequest;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Base implementation {@link lan.test.auth.PreAuthenticationService}
 * @author nik-lazer  15.07.2015   14:40
 */
public class RemoteAuthenticationServiceImpl extends BasePreAuthenticationServiceImpl<HttpServletRequest> {
	@Override
	protected String getUserName(HttpServletRequest servletRequest) {
		return servletRequest.getRemoteUser();
	}

	@Override
	protected void doAuth(HttpServletRequest servletRequest, String userName) {
		HttpSession session = servletRequest.getSession();
		session.setAttribute("currentUser", userName);
	}
}
