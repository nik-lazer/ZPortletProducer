package lan.test.portlet.zk;

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

/**
 * @see <a href="https://github.com/v0v87/zkoss-wsrp">zkoss-wsrp</a>
 * replace string "resourceStateForRewritewsrp_rewrite" to "resourceStateForRewrite/wsrp_rewrite"
 * in response body
 * @author solovyev.vladimir
 * @since 25.05.2015
 */
public class WebcenterWsrpEncoderFixResourceFilter implements ResourceFilter {
	private static final Logger log = LoggerFactory.getLogger(WebcenterWsrpEncoderFixResourceFilter.class);

	@Override
	public void doFilter(ResourceRequest resourceRequest, ResourceResponse resourceResponse, FilterChain filterChain) throws IOException, PortletException {
		String resourceID = resourceRequest.getResourceID();
		if (resourceID != null && resourceID.endsWith("zk.wpd")) {
			CustomPortletResponseWrapper newPortletResponse = new CustomPortletResponseWrapper(resourceResponse);
			filterChain.doFilter(resourceRequest, newPortletResponse);
			String response = new String(newPortletResponse.getByteArray());
			response = response.replaceAll("resourceStateForRewritewsrp_rewrite", "resourceStateForRewrite/wsrp_rewrite");
			PrintWriter writer = resourceResponse.getWriter();
			writer.println(response);
		} else{
			filterChain.doFilter(resourceRequest, resourceResponse);
		}
	}

	@Override
	public void init(FilterConfig paramFilterConfig) throws PortletException {
	}

	@Override
	public void destroy() {
	}
}
