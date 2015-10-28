package lan.test.auth;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Extracts user name from header
 * @author nik-lazer  19.08.2015   09:08
 */
public class WebspherePreAuthenticationImpl extends RemoteAuthenticationServiceImpl {
	@Override
	public String getUserName(HttpServletRequest servletRequest) {
		String userName = servletRequest.getHeader("OAM_REMOTE_USER");
		return userName;
	}
}
