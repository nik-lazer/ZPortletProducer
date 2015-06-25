package lan.test.auth;

import javax.servlet.ServletRequest;

/**
 * Interface for authentication service
 * @author nik-lazer  25.06.2015   15:41
 */
public interface PreAuthenticationService {
		void preAuth(ServletRequest servletRequest);

		void preAuth(ServletRequest servletRequest, String userName);
}
