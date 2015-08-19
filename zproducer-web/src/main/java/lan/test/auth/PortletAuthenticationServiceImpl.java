package lan.test.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Portlet authentication service
 * @author nik-lazer  17.08.2015   16:08
 */
public class PortletAuthenticationServiceImpl implements PortletAuthenticationService {
	private static final Logger log = LoggerFactory.getLogger(PortletAuthenticationServiceImpl.class);

	@Override
	public void preAuth(PortletRequest request, HttpServletRequest httpServletRequest) {
		String userName = request.getRemoteUser();
		log.debug("remoteUser={}", userName);
		if (userName == null) {
			Object userInfo = request.getAttribute(PortletRequest.USER_INFO);
			if (userInfo != null) {
				Map userInfoMap = (Map)userInfo;
				userName = (String) userInfoMap.get("user.login.id");
				log.debug("User.login.id={}", userName);
			}
		}
		preAuth(request, httpServletRequest, userName);
	}

	@Override
	public void preAuth(PortletRequest request, HttpServletRequest httpServletRequest, String userName) {
		HttpSession session = httpServletRequest.getSession();
		session.setAttribute("currentUser", userName);
	}
}
