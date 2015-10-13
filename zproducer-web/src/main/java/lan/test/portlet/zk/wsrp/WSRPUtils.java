package lan.test.portlet.zk.wsrp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.web.servlet.http.Encodes;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some methods for WSRP needs
 * @author nik-lazer  12.08.2015   13:43
 */
public class WSRPUtils {
	private static String replacement;
	private static Pattern pattern;

	private static final Logger log = LoggerFactory.getLogger(WSRPUtils.class);

	static {
		String regex = "(?<prefix>[^\\/])wsrp_rewrite(?<suffix>[^\\?])";
		replacement = "${prefix}/wsrp_rewrite${suffix}";
		pattern = Pattern.compile(regex);
	}

	/**
	 * Fixes error in webcenter portal then to urls in zk.wpd joins in one wrong url
	 * @param original
	 * @return
	 */
	public static String fixTokens(String original) {
		Matcher matcher = pattern.matcher(original);
		String response = matcher.replaceAll(replacement);
		return response;
	}

	public static String removeTokens(String data, String token, String delimiter) {
		String pattern = "[^" + delimiter + "]*" + token + "[^" + delimiter + "]*" + delimiter + "?";
		return data.replaceAll(pattern, "");
	}

	/**
	 * Overwrites source resourceUrl by means adding wsrp-url parameter. Takes protocol, host and port from actual HTTP request
	 * @param resourceUrl
	 * @param httpURL
	 * @param urlToEncode
	 * @return
	 */
	public static String overwriteWsrpUrl(String resourceUrl, String httpURL, String urlToEncode) {
		String encodedUrl = urlToEncode;
		try {
			URL reqUrl = null;
			try {
				reqUrl = new URL(httpURL);
				URL resUrl = new URL(reqUrl.getProtocol(), reqUrl.getHost(), reqUrl.getPort(), urlToEncode);
				if (log.isDebugEnabled()) {
					log.debug("resUrl: {}", resUrl);
				}
				encodedUrl = Encodes.encodeURIComponent(resUrl.toString());

			} catch (MalformedURLException e) {
				log.error("Parsing request URL error", e);
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Creating static URL encoding error", e);
		}
		return resourceUrl.replace("wsrp_rewrite?wsrp-urlType=resource&", "wsrp_rewrite?wsrp-urlType=resource&wsrp-url=" + encodedUrl + "&");
	}
}
