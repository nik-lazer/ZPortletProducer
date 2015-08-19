package lan.test.auth;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Extracts user name from header
 * @author nik-lazer  19.08.2015   09:08
 */
public class WebspherePreAuthenticationImpl extends PreAuthenticationServiceImpl {
	@Override
	public void preAuth(ServletRequest servletRequest) {
		String userName = ((HttpServletRequest) servletRequest).getHeader("OAM_REMOTE_USER");
		preAuth(servletRequest, userName);
	}
}
