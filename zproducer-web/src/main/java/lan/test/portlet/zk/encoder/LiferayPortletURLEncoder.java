package lan.test.portlet.zk.encoder;

import lan.test.portlet.zk.WSRPDhtmlLayoutPortlet;
import org.apache.commons.lang3.StringUtils;
import org.zkoss.web.servlet.http.Encodes;
import org.zkoss.web.util.resource.ClassWebResource;

import javax.portlet.MimeResponse;
import javax.portlet.ResourceURL;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * URLEncoder for liferay WSRP
 * @author nik-lazer
 */
public class LiferayPortletURLEncoder implements Encodes.URLEncoder {

	@Override
	public String encodeURL(final ServletContext ctx, final ServletRequest request, final ServletResponse response, String url, final Encodes.URLEncoder defaultEncoder) throws Exception {
		final MimeResponse portletResponse = (MimeResponse) request.getAttribute(WSRPDhtmlLayoutPortlet.PORTLET_RESPONSE);
		if (portletResponse != null) {
			HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response) {
				@Override
				public String encodeURL(String url) {
					if (request.getAttribute(WSRPDhtmlLayoutPortlet.CREATE_RESOURCE_URL) != null) {
						return createResourceUrl(portletResponse, url);
					}

					String updateUrl = ctx.getContextPath() + "/zkau";
					String rootUrl = ctx.getContextPath() + "/";
					String pathInfo = StringUtils.substringAfter(url, updateUrl);
					boolean isAuExtension = (StringUtils.isNotEmpty(pathInfo) && !pathInfo.startsWith(ClassWebResource.PATH_PREFIX)) || (isPortletMode(request) && isResourceUrl(url));

					if (isAuExtension) {
						ResourceURL resourceURL = portletResponse.createResourceURL();
						resourceURL.setResourceID(url);
						String resUrl = resourceURL.toString();
						if (StringUtils.equals(rootUrl, url)) {
							// Pretend url breaking in WpdExtendlet
							resUrl += "/";
						}
						return resUrl;
					}
					if (isPortletMode(request) && !isResourceUrl(url)) {
						return createStaticProxyUrl(ctx, url);
					}
					if (!url.startsWith("/")) {
						url = ctx.getContextPath() + "/" + url;
					}
					return super.encodeURL(url);
				}

			};
			return defaultEncoder.encodeURL(ctx, request, wrapper, url, defaultEncoder);
		} else {
			return defaultEncoder.encodeURL(ctx, request, response, url, defaultEncoder);
		}
	}

	private String createStaticProxyUrl(ServletContext ctx, String url) {
		String prefix = "/wsrp-resource/proxy?url=";
		String serverUrl = "http://producer.lan:28080";
		String retUrl = prefix + serverUrl + url;
		return retUrl;
	}

	private String createResourceUrl(MimeResponse portletResponse, String url) {
		ResourceURL resourceURL = portletResponse.createResourceURL();
		resourceURL.setResourceID(url);
		return resourceURL.toString();
	}

	private boolean isPortletMode(ServletRequest request) {
		return request.getAttribute(WSRPDhtmlLayoutPortlet.CREATE_PORTLET_URL_MODE) != null;
	}

	private boolean isResourceUrl(String url) {
		return StringUtils.endsWith(url, "zkau") || StringUtils.endsWith(url, "/") || StringUtils.endsWith(url, ".wcs");
	}
}