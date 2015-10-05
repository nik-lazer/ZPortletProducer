package lan.test.portlet.zk.util;

import com.liferay.portal.util.PortalUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppInit;

import javax.portlet.PortletRequest;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;

/**
 * Utils for application
 * @author nik-lazer  15.05.2015   12:20
 */
public class UIUtils implements WebAppInit {
	private static final Log log = Log.lookup(UIUtils.class);

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

	public static String getTextResource(ServletContext context, String resourcePath) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			is = context.getResourceAsStream(resourcePath);
			IOUtils.copy(is, os);
			os.flush();
			return os.toString();
		} finally {
			if (null != os) os.close();
			if (null != is) is.close();
			}
		}

	public static String getAbsoluteURL(PortletRequest request, String path){
		return PortalUtil.getPortalURL(request) + request.getContextPath() + path;
	}

	public static String getStaticURL(HttpServletRequest request, String path) {
		return PortalUtil.getStaticResourceURL(request, path);
	}

	public static <T> T loadComponentFromZul(String url) throws UiException {
		final Execution execution = Executions.getCurrent();
		if (execution != null) {
			try {
				return (T) execution.createComponents(url, null, null);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new UiException(e.getMessage(), e);
			}
		}
		throw new IllegalStateException("Unable to create component. Do you access it in ZK event listener?");
	}

	public static <T> T loadComponentFromZul(String url, Component parent, Map args) throws UiException {
		final Execution execution = Executions.getCurrent();
		if (execution != null) {
			try {
				return (T) execution.createComponents(url, parent, args);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new UiException(e.getMessage(), e);
			}
		}
		throw new IllegalStateException("Unable to create component. Do you access it in ZK event listener?");
	}

}
