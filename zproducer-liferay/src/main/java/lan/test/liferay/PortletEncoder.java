package lan.test.liferay;

import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.HttpUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * TODO: comment
 * @author nik-lazer  04.06.2015   17:01
 */
public class PortletEncoder implements URLEncoder {
	@Override
	public String encodeURL(HttpServletResponse response, String path) {
		String q = "/proxy?url=" + HttpUtil.encodeURL("/zksandbox.js.dsp");
		return path;
	}
}
