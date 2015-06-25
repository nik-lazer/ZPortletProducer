package lan.test.auth;

import lan.test.config.ApplicationContextProvider;
import lan.test.portlet.zk.util.UIUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zkoss.web.servlet.GenericFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Authentication filter
 * @author nik-lazer  25.06.2015   15:37
 */
public class PreAuthenticationFilter extends GenericFilter {

	@Override
	protected void init() throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		try {
			ApplicationContextProvider.getPreAuthService().preAuth(servletRequest);
		} finally {
			chain.doFilter(request, response);
		}
	}


	protected ApplicationContext getContext(FilterConfig filterConfig) {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
	}
}
