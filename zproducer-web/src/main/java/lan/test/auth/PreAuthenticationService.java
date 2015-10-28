package lan.test.auth;

import javax.portlet.PortletRequest;
import javax.servlet.ServletRequest;

/**
 * Interface for authentication service
 * @author nik-lazer  25.06.2015   15:41
 */
public interface PreAuthenticationService<T> {
	void preAuth(T servletRequest);
}
