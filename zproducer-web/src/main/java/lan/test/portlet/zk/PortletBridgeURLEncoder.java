package lan.test.portlet.zk;

import lan.test.config.ApplicationContextFactory;
import org.zkoss.web.servlet.http.Encodes;


import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * URLEncoder для запуска УФОСа как портлета
 * @author nik-lazer
 */
public class PortletBridgeURLEncoder implements Encodes.URLEncoder {
	private static final String WSRP_TOKEN = "wsrp_rewrite";

	public String encodeURL(ServletContext ctx, ServletRequest request, ServletResponse response, String uri, Encodes.URLEncoder defaultEncoder) throws Exception {
		if (uri.startsWith("images")) {
			uri = "/" + uri;
		}
		request.setAttribute("oracle.portlet.server.resourceRequiresRewriting", Boolean.FALSE);
		String defUrl = defaultEncoder.encodeURL(ctx, request, response, uri, defaultEncoder);
		request.removeAttribute("oracle.portlet.server.resourceRequiresRewriting");
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			if (!defUrl.startsWith(WSRP_TOKEN) && isPortletRequest(httpServletRequest)) {
				if (defUrl.startsWith(httpServletRequest.getContextPath())) {
					defUrl = defUrl.replaceFirst(httpServletRequest.getContextPath(), getPortalPrefix());
					defUrl += "?ses=" + ((HttpServletRequest) request).getRequestedSessionId();
				}
			}
			if ("/".equals(uri)) {
				defUrl = uri;
			}
		}

		return defUrl;
	}

	private boolean isPortletRequest(HttpServletRequest httpServletRequest) {
		String referer = httpServletRequest.getHeader("referer");
		String keyPath = getPortalContextPath() + "/" + getPortletKeyword();
		return referer != null && referer.contains(keyPath);
	}

	private String getPortalPrefix() {
		return "/" + getPortalContextPath() + getPortalSuffix();
	}

	private String getPortalSuffix() {
		return ApplicationContextFactory.getConfig().getResContextSuffix();
	}

	private String getPortalContextPath() {
		return ApplicationContextFactory.getConfig().getResContextPrefix();
	}

	private String getPortletKeyword() {
		return ApplicationContextFactory.getConfig().getResPortletKeyword();
	}
}