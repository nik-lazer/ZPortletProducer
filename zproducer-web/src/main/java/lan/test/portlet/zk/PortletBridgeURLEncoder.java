package lan.test.portlet.zk;

import org.zkoss.web.servlet.http.Encodes;


import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * URLEncoder для запуска УФОСа как портлета
 * @author Pobedenniy Alexey
 */
public class PortletBridgeURLEncoder implements Encodes.URLEncoder {
	private static final String WSRP_TOKEN = "wsrp_rewrite";
	private String portalPath = "Application1-Portal-context-root";

	public String encodeURL(ServletContext ctx, ServletRequest request, ServletResponse response, String uri, Encodes.URLEncoder defaultEncoder) throws Exception {
		if (uri.startsWith("images")) {
			uri = "/" + uri;
		}
		String defUrl = defaultEncoder.encodeURL(ctx, request, response, uri, defaultEncoder);
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
		return "/resourceproxy/cache/portlets/resources";
	}

	private String getPortalContextPath() {
		return portalPath;
	}

	private String getPortletKeyword() {
		return "adfportlet";
	}
}