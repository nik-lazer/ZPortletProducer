package lan.test.portlet.zk.wsrp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some methods for WSRP needs
 * @author nik-lazer  12.08.2015   13:43
 */
public class WSRPUtils {
	private static String replacement;
	private static Pattern pattern;

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

}
