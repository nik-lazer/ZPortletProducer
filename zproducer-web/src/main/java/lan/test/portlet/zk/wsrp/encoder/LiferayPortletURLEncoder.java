package lan.test.portlet.zk.wsrp.encoder;

import lan.test.portlet.zk.wsrp.WSRPUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.web.servlet.http.Encodes;
import org.zkoss.web.util.resource.ClassWebResource;
import org.zkoss.zk.ui.Executions;

import javax.portlet.MimeResponse;
import javax.portlet.ResourceURL;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * URLEncoder for liferay WSRP
 * @author nik-lazer
 */
public class LiferayPortletURLEncoder extends WebcenterPortletURLEncoder implements Encodes.URLEncoder {

	/**
	 * Generates URL for static resources, such as dsp and other. It needs because response.encodeURL in Liferay returns the same value
	 * (unlike Webcenter, which generates normal resource URL)
	 * @param request
	 * @param portletResponse
	 * @param url
	 * @return
	 */
	@Override
	protected String createDefaultPortletUrl(HttpServletRequest request, MimeResponse portletResponse, String url) {
		return WSRPUtils.overwriteWsrpUrl(portletResponse.createResourceURL().toString(), request.getRequestURL().toString(), url);
	}
}