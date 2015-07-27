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
 * WSRP resources tracing
 * @author nik-lazer  24.07.2015   16:55
 */
public class WsrpTraceResourceFilter implements ResourceFilter {
	private static final Logger log = LoggerFactory.getLogger(WsrpTraceResourceFilter.class);

	@Override
	public void doFilter(ResourceRequest resourceRequest, ResourceResponse resourceResponse, FilterChain filterChain) throws IOException, PortletException {
		String resourceID = resourceRequest.getResourceID();
		if (resourceID != null && (resourceID.endsWith(".wpd") || (resourceID.endsWith(".wcs")))) {
			CustomPortletResponseWrapper newPortletResponse = new CustomPortletResponseWrapper(resourceResponse);
			filterChain.doFilter(resourceRequest, newPortletResponse);
			String response = new String(newPortletResponse.getByteArray());
			log.warn("WSRP RESPONSE '" + resourceID + "' START");
			log.warn(response);
			log.warn("WSRP RESPONSE '\" + resourceID + \"' END");
			PrintWriter writer = resourceResponse.getWriter();
			writer.println(response);
		} else{
			filterChain.doFilter(resourceRequest, resourceResponse);
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {

	}

	@Override
	public void destroy() {

	}
}
