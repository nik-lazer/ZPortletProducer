package lan.test.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

/**
 * Portlet authentication service
 * @author nik-lazer  17.08.2015   16:08
 */
public class PortletAuthenticationServiceImpl implements PortletAuthenticationService {
	private static final Logger log = LoggerFactory.getLogger(PortletAuthenticationServiceImpl.class);

	@Override
	public void preAuth(PortletRequest request) {
		String userName = request.getRemoteUser();
		log.warn("PASL: user=" + userName);
		preAuth(request, userName);
	}

	@Override
	public void preAuth(PortletRequest request, String userName) {
		PortletSession session = request.getPortletSession();
		session.setAttribute("currentUser", userName);
	}
}
