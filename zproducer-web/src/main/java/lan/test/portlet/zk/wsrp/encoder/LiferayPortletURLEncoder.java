package lan.test.portlet.zk.wsrp.encoder;

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

	private static final Logger log = LoggerFactory.getLogger(PortletBridgeURLEncoder.class);

	@Override
	protected String createDefaultPortletUrl(HttpServletRequest request, MimeResponse portletResponse, String url) {
		String resourceURL = portletResponse.createResourceURL().toString();
		String encodedUrl = url;
		try {
			String requestUrl = request.getRequestURL().toString();
			URL reqUrl = null;
			try {
				reqUrl = new URL(requestUrl);
				URL resUrl = new URL(reqUrl.getProtocol(), reqUrl.getHost(), reqUrl.getPort(), url);
				log.debug("resUrl: {}", resUrl);
				encodedUrl = Encodes.encodeURIComponent(resUrl.toString());

			} catch (MalformedURLException e) {
				log.error("Parsing request URL error", e);
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Creating static URL encoding error", e);
		}
		return resourceURL.replace("wsrp_rewrite?wsrp-urlType=resource&", "wsrp_rewrite?wsrp-urlType=resource&wsrp-url=" + encodedUrl + "&");
	}
}