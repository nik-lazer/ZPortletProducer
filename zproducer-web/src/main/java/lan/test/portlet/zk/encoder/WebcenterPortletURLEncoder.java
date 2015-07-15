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
 * URL Encoder for webcenter consumer
 * @author nik-lazer  15.07.2015   16:31
 */
public class WebcenterPortletURLEncoder implements Encodes.URLEncoder {
	@Override
	public String encodeURL(final ServletContext ctx, final ServletRequest request, ServletResponse response, String url, Encodes.URLEncoder defaultEncoder) throws Exception {
		final MimeResponse portletResponse = (MimeResponse) request.getAttribute(WSRPDhtmlLayoutPortlet.PORTLET_RESPONSE);
		if (portletResponse != null) {
			HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response) {
				@Override
				public String encodeURL(String url) {
					if (request.getAttribute(WSRPDhtmlLayoutPortlet.CREATE_RESOURCE_URL) != null) {
						return createResourceUrl(portletResponse, url);
					}

					String updateUrl = ctx.getContextPath() + "/zkau";
					String pathInfo = StringUtils.substringAfter(url, updateUrl);
					boolean isAuExtension = StringUtils.isNotEmpty(pathInfo) && !pathInfo.startsWith(ClassWebResource.PATH_PREFIX);

					if (isAuExtension) {
						ResourceURL resourceURL = portletResponse.createResourceURL();
						resourceURL.setResourceID(url);
						return resourceURL.toString();
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

	private String createResourceUrl(MimeResponse portletResponse, String url) {
		ResourceURL resourceURL = portletResponse.createResourceURL();
		resourceURL.setResourceID(url);
		return resourceURL.toString();
	}
}
