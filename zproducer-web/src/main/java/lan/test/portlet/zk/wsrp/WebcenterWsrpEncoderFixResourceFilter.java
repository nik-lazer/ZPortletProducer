package lan.test.portlet.zk.wsrp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.ResourceFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @see <a href="https://github.com/v0v87/zkoss-wsrp">zkoss-wsrp</a>
 * replace string "resourceStateForRewritewsrp_rewrite" to "resourceStateForRewrite/wsrp_rewrite"
 * in response body
 * @author solovyev.vladimir
 * @since 25.05.2015
 */
public class WebcenterWsrpEncoderFixResourceFilter implements ResourceFilter {
	private static final Logger log = LoggerFactory.getLogger(WebcenterWsrpEncoderFixResourceFilter.class);
	private Pattern pattern;
	private String replacement;

	@Override
	public void doFilter(ResourceRequest resourceRequest, ResourceResponse resourceResponse, FilterChain filterChain) throws IOException, PortletException {
		String resourceID = resourceRequest.getResourceID();
		if (resourceID != null && resourceID.endsWith("zk.wpd")) {
			CustomPortletResponseWrapper newPortletResponse = new CustomPortletResponseWrapper(resourceResponse);
			filterChain.doFilter(resourceRequest, newPortletResponse);
			log.debug("response encoding: " + resourceResponse.getCharacterEncoding());
			String response = new String(newPortletResponse.getByteArray(), resourceResponse.getCharacterEncoding());
			response = fixTokens(response);
			PrintWriter writer = resourceResponse.getWriter();
			writer.println(response);
		} else{
			filterChain.doFilter(resourceRequest, resourceResponse);
		}
	}

	String fixTokens(String original) {
		Matcher matcher = pattern.matcher(original);
		String response = matcher.replaceAll(replacement);
		return response;
	}

	@Override
	public void init(FilterConfig paramFilterConfig) throws PortletException {
		String regex = "(?<prefix>[^\\/])wsrp_rewrite(?<suffix>[^\\?])";
		replacement = "${prefix}/wsrp_rewrite${suffix}";
		pattern = Pattern.compile(regex);
	}

	@Override
	public void destroy() {
	}
}
