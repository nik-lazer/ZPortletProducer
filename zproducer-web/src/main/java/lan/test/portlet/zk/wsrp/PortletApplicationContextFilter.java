package lan.test.portlet.zk.wsrp;

import lan.test.config.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.web.portlet.context.PortletApplicationContextUtils;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;
import java.io.IOException;

/**
 * @author pobedenniy.alexey
 * @since 24.08.2015
 */
public class PortletApplicationContextFilter implements ResourceFilter, RenderFilter, ActionFilter {
	private FilterConfig filterConfig;

	@Override
	public void doFilter(RenderRequest request, RenderResponse response, FilterChain chain) throws IOException, PortletException {
		try {
			ApplicationContext applicationContext = PortletApplicationContextUtils.getWebApplicationContext(filterConfig.getPortletContext());
			ApplicationContextProvider.setThreadApplicationContext(applicationContext);
		} finally {
			try {
				chain.doFilter(request, response);
			} finally {
				ApplicationContextProvider.setThreadApplicationContext(null);
			}
		}
	}

	@Override
	public void doFilter(ActionRequest request, ActionResponse response, FilterChain chain) throws IOException, PortletException {
		try {
			ApplicationContext applicationContext = PortletApplicationContextUtils.getWebApplicationContext(filterConfig.getPortletContext());
			ApplicationContextProvider.setThreadApplicationContext(applicationContext);
		} finally {
			try {
				chain.doFilter(request, response);
			} finally {
				ApplicationContextProvider.setThreadApplicationContext(null);
			}
		}
	}

	@Override
	public void doFilter(ResourceRequest request, ResourceResponse response, FilterChain chain) throws IOException, PortletException {
		try {
			ApplicationContext applicationContext = PortletApplicationContextUtils.getWebApplicationContext(filterConfig.getPortletContext());
			ApplicationContextProvider.setThreadApplicationContext(applicationContext);
		} finally {
			try {
				chain.doFilter(request, response);
			} finally {
				ApplicationContextProvider.setThreadApplicationContext(null);
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {

	}
}