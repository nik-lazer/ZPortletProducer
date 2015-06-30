package lan.test.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import lan.test.auth.PreAuthenticationService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Implementation {@link lan.test.auth.PreAuthenticationService} for liferay WSRP portlet
 * @author nik-lazer  25.06.2015   15:46
 */
public class LiferayPreAuthenticationServiceImpl implements PreAuthenticationService {
	@Override
	public void preAuth(ServletRequest servletRequest) {
		String userId = ((HttpServletRequest) servletRequest).getRemoteUser();
		User user = null;
		if (userId != null) {
			try {
				user = UserLocalServiceUtil.getUser(Long.valueOf(userId));
				String userName = user.getScreenName();
				preAuth(servletRequest, userName);
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void preAuth(ServletRequest servletRequest, String userName) {
		HttpSession session = ((HttpServletRequest) servletRequest).getSession();
		session.setAttribute("currentUser", userName);
	}
}
