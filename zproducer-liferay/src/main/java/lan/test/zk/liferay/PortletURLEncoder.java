package lan.test.zk.liferay;

import org.zkoss.web.servlet.http.Encodes;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * URLEncoder для запуска УФОСа как портлета в liferay
 * @author nik-lazer
 */
public class PortletURLEncoder implements Encodes.URLEncoder {

	public String encodeURL(ServletContext ctx, ServletRequest request, ServletResponse response, String uri, Encodes.URLEncoder defaultEncoder) throws Exception {
		String defUrl = defaultEncoder.encodeURL(ctx, request, response, uri, defaultEncoder);
		return defUrl;
	}
}