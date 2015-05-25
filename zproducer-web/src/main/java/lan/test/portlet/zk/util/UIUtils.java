package lan.test.portlet.zk.util;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppInit;

import java.util.Calendar;

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

	public static String resolveFileName(Calendar calendar) {
		String format = "print-%td-%1$tm-%1$tY %1$tH%1$tM-%1$tS.txt";
		return String.format(format, calendar);
	}

}
