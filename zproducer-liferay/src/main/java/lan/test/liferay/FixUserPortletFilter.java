package lan.test.liferay;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.PortletUtil;
import com.liferay.portal.util.PortalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.ActionRequestWrapper;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.RenderRequestWrapper;
import javax.portlet.filter.ResourceFilter;
import javax.portlet.filter.ResourceRequestWrapper;
import java.io.IOException;

/**
 * Fixs user name in portlet: gets remote user name (it's always in lower case) and asks portal for actual user
 * Users's uuis is saved into {@link javax.portlet.PortletSession}
 * @author nik-lazer  23.10.2015   17:22
 */
public class FixUserPortletFilter implements ResourceFilter, RenderFilter, ActionFilter {
	private static final Logger log = LoggerFactory.getLogger(FixUserPortletFilter.class);

	public static final String USER_ATTR_NAME = "liferay.fixed.user";

	@Override
	public void doFilter(ActionRequest request, ActionResponse response, FilterChain chain) throws IOException, PortletException {
		try {
			try {
				fixUser(request);
			} catch (PortalException e) {
				log.error("PortalException in action filter", e);
			} catch (SystemException e) {
				log.error("SystemException in action filter", e);
			}
		} finally {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void doFilter(RenderRequest request, RenderResponse response, FilterChain chain) throws IOException, PortletException {
		try {
			try {
				fixUser(request);
			} catch (PortalException e) {
				log.error("PortalException in render filter", e);
			} catch (SystemException e) {
				log.error("SystemException in render filter", e);
			}
		} finally {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void doFilter(ResourceRequest request, ResourceResponse response, FilterChain chain) throws IOException, PortletException {
		try {
			try {
				fixUser(request);
			} catch (PortalException e) {
				log.error("PortalException in resource filter", e);
			} catch (SystemException e) {
				log.error("SystemException in resource filter", e);
			}
		} finally {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {

	}

	@Override
	public void destroy() {

	}

	private void fixUser(PortletRequest portletRequest) throws SystemException, PortalException {
		PortletSession portletSession = portletRequest.getPortletSession(false);
		if (portletSession != null) {
			String userName = (String)portletSession.getAttribute(USER_ATTR_NAME);
			if (userName == null) {
				final User user = PortalUtil.getUser(portletRequest);
				if (user != null) {
					if (log.isDebugEnabled()) {
						log.debug("User {} is set into session");
					}
					portletSession.setAttribute(USER_ATTR_NAME, user.getUserUuid());
				}
			}
		}
	}
}
