package lan.test.portlet.zk.wsrp.encoder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.web.servlet.http.Encodes;
import org.zkoss.web.util.resource.ClassWebResource;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

import javax.portlet.ActionRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @see <a href="https://github.com/v0v87/zkoss-wsrp">zkoss-wsrp</a>
 * Обработка урлов в режиме портлета
 * основные мысли как работать с урлами взяты из
 * http://www.oracle.com/technetwork/java/jsr286-2-141964.html
 * @author solovyev.vladimir
 * @since 21.05.2015
 */
public class WebcenterPortletURLEncoder implements Encodes.URLEncoder {
	private static final Logger log = LoggerFactory.getLogger(WebcenterPortletURLEncoder.class);
	// См http://docs.oracle.com/cd/E16764_01/webcenter.1111/e10148/jpsdg_java_adv.htm#BABGACEG
	// How to Implement Stateless Resource Proxying
	public static final String ORACLE_PORTLET_SERVER_USE_STATELESS_PROXYING = "oracle.portlet.server.useStatelessProxying";
	public static final String ORACLE_WEBCENTER_PORTLET_RESPONSE = "oracle.webcenter.portlet.response";

	public static final String CREATE_RESOURCE_URL = "create.resource.url";
	public static final String UPLOAD_ACTION_ID = "upload";

	@Override
	public String encodeURL(final ServletContext ctx, final ServletRequest request, ServletResponse response, String url, Encodes.URLEncoder defaultEncoder) throws Exception {
		final MimeResponse portletResponse = (MimeResponse) request.getAttribute(ORACLE_WEBCENTER_PORTLET_RESPONSE);
		if (portletResponse != null) {
			HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response) {
				@Override
				public String encodeURL(String url) {
					if (request.getAttribute(CREATE_RESOURCE_URL) != null) {
						return createResourceUrl(portletResponse, url);
					}

					String updateUrl = ctx.getContextPath() + "/zkau";
					String pathInfo = StringUtils.substringAfter(url, updateUrl);
					boolean isAuExtension = StringUtils.isNotEmpty(pathInfo) && !pathInfo.startsWith(ClassWebResource.PATH_PREFIX);

					if (isAuExtension || url.endsWith("wcs") || pathInfo.endsWith(".css.dsp")) {
						return createResourceUrl(portletResponse, url);
					}
					if (!isAuExtension && pathInfo.endsWith("/upload")) {
						return createActionURL(portletResponse, UPLOAD_ACTION_ID);
					}
					if (url.endsWith("/upload")) {
						return createUploadURL((HttpServletRequest) request, portletResponse, super.encodeURL(url));
					}
					// Запрос ресурса через javax.portlet.ResourceServingPortlet.serveResource()
					// zk.wcs zk.wpd файлы внутри которых есть урлы для rewrite
					request.setAttribute(ORACLE_PORTLET_SERVER_USE_STATELESS_PROXYING, Boolean.TRUE);
					if (!url.startsWith("/")) {
						// если не сделать java.lang.IllegalArgumentException: Relative path [1.jpg] must start with a '/'
						url = ctx.getContextPath() + "/" + url;
					}
					return createDefaultPortletUrl((HttpServletRequest) request, portletResponse, super.encodeURL(url));
				}

			};
			return defaultEncoder.encodeURL(ctx, request, wrapper, url, defaultEncoder);

		} else {
			return defaultEncoder.encodeURL(ctx, request, response, url, defaultEncoder);
		}
	}

	protected String createUploadURL(HttpServletRequest httpServletRequest, MimeResponse portletResponse, String url) {
		return createDefaultPortletUrl(httpServletRequest, portletResponse, url);
	}

	public static String createResourceUrl(ServletContext ctx, ServletRequest request, ServletResponse response, String uri) throws ServletException {
		try {
			request.setAttribute(CREATE_RESOURCE_URL, Boolean.TRUE);
			return Encodes.encodeURL(ctx, request, response, uri);
		} finally {
			request.removeAttribute(CREATE_RESOURCE_URL);
		}
	}

	protected String createResourceUrl(MimeResponse portletResponse, String url) {
		ResourceURL resourceURL = portletResponse.createResourceURL();
		resourceURL.setResourceID(url);
		return resourceURL.toString();
	}

	private String createActionURL(MimeResponse portletResponse, String actionId) {
		PortletURL actionURL = portletResponse.createActionURL();
		actionURL.setParameter(ActionRequest.ACTION_NAME, actionId);
		return actionURL.toString();
	}

	public static String encodeActionURL(String uri) {
		Execution current = Executions.getCurrent();
		return current.encodeURL(uri);
	}


	protected String createDefaultPortletUrl(HttpServletRequest request, MimeResponse portletResponse, String url) {
		return url;
	}

}
