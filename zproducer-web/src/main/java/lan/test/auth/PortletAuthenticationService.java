package lan.test.auth;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Interface for portlet authentication service
 * @author nik-lazer  17.08.2015   16:09
 */
public interface PortletAuthenticationService {
	void preAuth(PortletRequest request, HttpServletRequest httpServletRequest);
	void preAuth(PortletRequest request, HttpServletRequest httpServletRequest, String userName);
}
