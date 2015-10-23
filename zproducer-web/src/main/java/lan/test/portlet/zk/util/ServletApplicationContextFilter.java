package lan.test.portlet.zk.util;

import lan.test.config.ApplicationContextProvider;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author nik-lazer  23.10.2015   12:11
 */
public class ServletApplicationContextFilter implements Filter {
	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		try {
			WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
			ApplicationContextProvider.setThreadApplicationContext(applicationContext);
		} finally {
			try {
				filterChain.doFilter(servletRequest, servletResponse);
			} finally {
				ApplicationContextProvider.setThreadApplicationContext(null);
			}
		}
	}

	@Override
	public void destroy() {
	}

}
