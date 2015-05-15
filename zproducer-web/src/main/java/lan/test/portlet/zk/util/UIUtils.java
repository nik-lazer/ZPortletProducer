package lan.test.portlet.zk.util;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppInit;

/**
 * Utils for application
 * @author nik-lazer  15.05.2015   12:20
 */
public class UIUtils implements WebAppInit {
	private static WebApp wapp;
	private static String servletContextPath;

	public void init(WebApp _wapp) {
		wapp = _wapp;

		servletContextPath = wapp.getServletContext().getContextPath();
	}

	public static String getServletContextPath() {
		return servletContextPath;
	}

}
