package lan.test.auth;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Base implementation {@link lan.test.auth.PreAuthenticationService}
 * @author nik-lazer  15.07.2015   14:40
 */
public class BasePreAuthenticationServiceImpl implements PreAuthenticationService {
	@Override
	public void preAuth(ServletRequest servletRequest) {
		String userName = ((HttpServletRequest) servletRequest).getRemoteUser();
		preAuth(servletRequest, userName);
	}

	@Override
	public void preAuth(ServletRequest servletRequest, String userName) {
		HttpSession session = ((HttpServletRequest) servletRequest).getSession();
		session.setAttribute("currentUser", userName);
	}
}
