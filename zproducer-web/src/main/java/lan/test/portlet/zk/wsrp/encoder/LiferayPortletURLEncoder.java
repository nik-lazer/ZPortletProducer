package lan.test.portlet.zk.wsrp.encoder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.web.servlet.http.Encodes;
import org.zkoss.web.util.resource.ClassWebResource;

import javax.portlet.MimeResponse;
import javax.portlet.ResourceURL;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * URLEncoder for liferay WSRP
 * @author nik-lazer
 */
public class LiferayPortletURLEncoder implements Encodes.URLEncoder {

	private static final Logger log = LoggerFactory.getLogger(PortletBridgeURLEncoder.class);
	// См http://docs.oracle.com/cd/E16764_01/webcenter.1111/e10148/jpsdg_java_adv.htm#BABGACEG
	// How to Implement Stateless Resource Proxying
	public static final String ORACLE_PORTLET_SERVER_USE_STATELESS_PROXYING = "oracle.portlet.server.useStatelessProxying";
	public static final String ORACLE_WEBCENTER_PORTLET_RESPONSE = "oracle.webcenter.portlet.response";

	public static final String CREATE_RESOURCE_URL = "create.resource.url";

	@Override
	public String encodeURL(final ServletContext ctx, final ServletRequest request, ServletResponse response, String url, Encodes.URLEncoder defaultEncoder) throws Exception {
		final MimeResponse portletResponse = (MimeResponse) request.getAttribute(ORACLE_WEBCENTER_PORTLET_RESPONSE);
		if (portletResponse != null) {
			HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response) {
				@Override
				public String encodeURL(String url) {
					if (request.getAttribute(CREATE_RESOURCE_URL) != null) {
						String ret = createResourceUrl(portletResponse, url);
						log.debug("RURL: {} {}", url, ret);
						return ret;
					}

					String updateUrl = ctx.getContextPath() + "/zkau";
					String pathInfo = StringUtils.substringAfter(url, updateUrl);
					boolean isAuExtension = StringUtils.isNotEmpty(pathInfo) && !pathInfo.startsWith(ClassWebResource.PATH_PREFIX);

					if (isAuExtension || url.endsWith("wcs")) {
						String ret = createResourceUrl(portletResponse, url);
						log.debug("auURL: {} {}", url, ret);
						return  ret;
					}
					// Запрос ресурса через javax.portlet.ResourceServingPortlet.serveResource()
					// zk.wcs zk.wpd файлы внутри которых есть урлы для rewrite
					request.setAttribute(ORACLE_PORTLET_SERVER_USE_STATELESS_PROXYING, Boolean.TRUE);
					if (!url.startsWith("/")) {
						// если не сделать java.lang.IllegalArgumentException: Relative path [1.jpg] must start with a '/'
						url = ctx.getContextPath() + "/" + url;
					}
					String ret = super.encodeURL(url);
					log.debug("superURL: {} {}", url, ret);
					return super.encodeURL(url);
				}

			};
			String ret = defaultEncoder.encodeURL(ctx, request, wrapper, url, defaultEncoder);
			log.debug("PURL: {} {}", url, ret);
			return ret;

		} else {
			String ret = defaultEncoder.encodeURL(ctx, request, response, url, defaultEncoder);
			log.debug("SURL: {} {}", url, ret);
			return ret;
		}
	}

	public static String createResourceUrl(ServletContext ctx, ServletRequest request, ServletResponse response, String uri) throws ServletException {
		try {
			request.setAttribute(CREATE_RESOURCE_URL, Boolean.TRUE);
			return Encodes.encodeURL(ctx, request, response, uri);
		} finally {
			request.removeAttribute(CREATE_RESOURCE_URL);
		}
	}

	private String createResourceUrl(MimeResponse portletResponse, String url) {
		ResourceURL resourceURL = portletResponse.createResourceURL();
		resourceURL.setResourceID(url);
		return resourceURL.toString();
	}
}