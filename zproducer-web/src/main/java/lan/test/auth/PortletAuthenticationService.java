package lan.test.auth;

import javax.portlet.PortletRequest;

/**
 * Interface for portlet authentication service
 * @author nik-lazer  17.08.2015   16:09
 */
public interface PortletAuthenticationService {
	void preAuth(PortletRequest request);
	void preAuth(PortletRequest request, String userName);
}
