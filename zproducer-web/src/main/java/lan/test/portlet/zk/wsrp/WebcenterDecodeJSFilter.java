package lan.test.portlet.zk.wsrp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author nik-lazer  18.11.2015   13:06
 */
public class WebcenterDecodeJSFilter implements RenderFilter {
	private static final Logger log = LoggerFactory.getLogger(WebcenterDecodeJSFilter.class);

	@Override
	public void doFilter(RenderRequest renderRequest, RenderResponse renderResponse, FilterChain filterChain) throws IOException, PortletException {
		CustomPortletRenderResponseWrapper newPortletResponse = new CustomPortletRenderResponseWrapper(renderResponse);
		filterChain.doFilter(renderRequest, newPortletResponse);
		log.debug("response encoding: " + renderResponse.getCharacterEncoding());
		String response = new String(newPortletResponse.getByteArray(), renderResponse.getCharacterEncoding());
		response = WSRPUtils.decodeJavaScript(response);
		PrintWriter writer = renderResponse.getWriter();
		writer.println(response);
	}

	@Override
	public void init(FilterConfig paramFilterConfig) throws PortletException {
	}

	@Override
	public void destroy() {
	}
}
