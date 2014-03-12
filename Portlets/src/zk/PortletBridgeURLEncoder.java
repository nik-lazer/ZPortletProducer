package zk;

import org.zkoss.web.portlet.PortletHttpSession;
import org.zkoss.web.servlet.http.Encodes.URLEncoder;


import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * URLEncoder для запуска УФОСа как портлета
 * @author Pobedenniy Alexey
 */
public class PortletBridgeURLEncoder implements URLEncoder {
	private static final String WSRP_TOKEN = "wsrp_rewrite";
	private static final String CUSTOM_TOKEN = "custom_rewrite";

	@Override
	public String encodeURL(ServletContext ctx, ServletRequest request, ServletResponse response, String uri, URLEncoder defaultEncoder) throws Exception {
		if (uri.startsWith("images")) {
			uri = "/"+uri;
		}
		String defUrl = null;
		if (uri.endsWith("zkau")) {
			defUrl = "/Application1-Portal-context-root/resourceproxy/cache/portlets/resources" + uri;
		} else {
			defUrl = defaultEncoder.encodeURL(ctx, request, response, uri, defaultEncoder);
		}
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			if (!defUrl.startsWith(WSRP_TOKEN) && (isPortletRequest(httpServletRequest))) {
				if (defUrl.startsWith(httpServletRequest.getContextPath())) {
					defUrl = defUrl.replaceFirst(httpServletRequest.getContextPath(), "/Application1-Portal-context-root/resourceproxy/cache/portlets/resources");
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
		return (referer != null && referer.contains("Application1-Portal-context-root"));
	}

}