package lan.test.portlet.zk.wsrp;

import com.google.common.io.BaseEncoding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.lang.Strings;
import org.zkoss.web.servlet.http.Encodes;

import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some methods for WSRP needs
 * @author nik-lazer  12.08.2015   13:43
 */
public class WSRPUtils {
	private static String replacement;
	private static Pattern pattern;
	private static Pattern decodeJSPattern;
	private static Pattern hexPattern;

	private static final Logger log = LoggerFactory.getLogger(WSRPUtils.class);

	static {
		String regex = "(?<prefix>[^\\/])wsrp_rewrite(?<suffix>[^\\?])";
		replacement = "${prefix}/wsrp_rewrite${suffix}";
		pattern = Pattern.compile(regex);
		String decodeJSRegex = "wsrp_rewrite\\\\x3F.*?\\\\x2Fwsrp_rewrite";
		decodeJSPattern = Pattern.compile(decodeJSRegex);
		hexPattern = Pattern.compile("\\\\x[0-9A-F]{2}+");
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

	/**
	 * Sets Content-Disposition for getting correct downloaded file name. It needs because Zkoss doesn't set this header properly
	 * (at least in versions 7.0.1 and 7.0.4)
	 * @param response
	 * @param httpreq
	 * @param resourceID
	 * @throws UnsupportedEncodingException
	 */
	public static void fixContentDisposition(PortletResponse response, HttpServletRequest httpreq, String resourceID) throws UnsupportedEncodingException {
		String fileName = URLDecoder.decode(resourceID.substring(resourceID.lastIndexOf("/") + 1), "UTF-8");
		String contentDisposition = "attachment";
		if (StringUtils.isNotEmpty(fileName)) {
			contentDisposition += ";filename=" + encodeFilename(httpreq, fileName);
		}
		response.setProperty("Content-Disposition", contentDisposition);
	}

	/**
	 * Method copied from {@link org.zkoss.web.servlet.http.Https.encodeFilename()} for using in WSRP portlet
	 * @param request
	 * @param filename
	 * @return
	 */
	@SuppressWarnings("ALL")
	public static String encodeFilename(HttpServletRequest request, String filename) {
		String agent = request.getHeader("USER-AGENT");
		if (agent != null) {
			try {
				if (agent.contains("Trident")) {
					filename = URLEncoder.encode(filename, "UTF-8");
				} else if (agent.contains("Mozilla")) {
					byte[] bytes = filename.getBytes("UTF-8");
					filename = "";
					for (byte b: bytes) {
						filename += (char)(b & 0xff);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return '"' + Strings.escape(filename, "\"") + '"';
	}

	public static String decodeJavaScript(String data) {
		StringBuilder response = new StringBuilder();

		Matcher matcher = decodeJSPattern.matcher(data);
		int mainIndex = 0;
		while (matcher.find()) {
			StringBuilder sb = new StringBuilder();
			String match = matcher.group();
			Matcher hexMatcher = hexPattern.matcher(match);
			int index = 0;
			while (hexMatcher.find()) {
				String symbol = hexMatcher.group();
				String decoded = new String(BaseEncoding.base16().decode(symbol.replace("\\x", "")));
				sb.append(match.substring(index, hexMatcher.start())).append(decoded);
				index = hexMatcher.end();
			}
			sb.append(match.substring(index));
			response.append(data.substring(mainIndex, matcher.start())).append(sb);
			mainIndex = matcher.end();
		}
		response.append(data.substring(mainIndex));
		return response.toString();
	}
}
