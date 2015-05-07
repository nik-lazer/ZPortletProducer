package lan.test.portlet.zk;

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
	private String portalPath = "Application7-Portal-context-root";

	public String encodeURL(ServletContext ctx, ServletRequest request, ServletResponse response, String uri, Encodes.URLEncoder defaultEncoder) throws Exception {
		if (uri.startsWith("images")) {
			uri = "/" + uri;
		}
		String defUrl = defaultEncoder.encodeURL(ctx, request, response, uri, defaultEncoder);
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			if (!defUrl.startsWith(WSRP_TOKEN)) {
				if (defUrl.startsWith(httpServletRequest.getContextPath())) {
					defUrl = defUrl.replaceFirst(httpServletRequest.getContextPath(), getPortalPrefix());
					HttpSession session = httpServletRequest.getSession();
					if (session != null) {
						defUrl += "?ses=" + session.getId();
					}
				}
			}
			if ("/".equals(uri)) {
				defUrl = uri;
			}
		}

		return defUrl;
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
}