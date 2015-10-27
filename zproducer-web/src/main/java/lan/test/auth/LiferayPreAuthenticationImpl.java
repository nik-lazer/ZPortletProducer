package lan.test.auth;

import javax.portlet.PortletRequest;
import javax.servlet.ServletRequest;

/**
 * Implementation of {@link lan.test.auth.PreAuthenticationService} for Liferay. It's used for
 * getting correct user's name, beacuse screenName in Liferay is always in lower case
 * @author nik-lazer  23.10.2015   17:47
 */
public class LiferayPreAuthenticationImpl extends PreAuthenticationServiceImpl {
	public static final String USER_ATTR_NAME = "liferay.fixed.user";

	@Override
	public void preAuth(PortletRequest portletRequest, ServletRequest servletRequest) {
		String userName = (String) portletRequest.getPortletSession().getAttribute(USER_ATTR_NAME);
		preAuth(servletRequest, userName);
	}
}
