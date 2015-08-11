package lan.test.portlet.zk.wsrp.encoder;

import lan.test.portlet.zk.wsrp.session.DualSessionHelper;
import org.zkoss.web.portlet.RenderHttpServletRequest;
import org.zkoss.web.servlet.http.Encodes;
import org.zkoss.zk.ui.Executions;

import javax.portlet.PortletRequest;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Encoder for liferay portal, which adds portlet session identifier to zkau URL
 * @author nik-lazer  10.08.2015   11:41
 */
public class LiferayProxiedPortletURLEncoder implements Encodes.URLEncoder {
	@Override
	public String encodeURL(ServletContext ctx, ServletRequest request, ServletResponse response, String url, Encodes.URLEncoder defaultEncoder) throws Exception {
		if (url.endsWith("zkau")) {
			if (request instanceof RenderHttpServletRequest) {
				HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				url = url + "?" + DualSessionHelper.PORTLET_SESSION_ID + "=" + httpServletRequest.getSession().getId();
			}
		}
		return defaultEncoder.encodeURL(ctx, request, response, url, defaultEncoder);
	}
}
