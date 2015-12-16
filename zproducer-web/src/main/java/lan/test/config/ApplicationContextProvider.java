package lan.test.config;

import com.google.common.cache.Cache;
import lan.test.auth.PortletAuthenticationService;
import lan.test.auth.PreAuthenticationService;
import lan.test.portlet.zk.history.WebBrowserHistoryManager;
import lan.test.rmi.BookServiceProxyBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Application context provider
 * @author nik-lazer  20.05.2015   10:19
 */
public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext context;

	private static ThreadLocal<ApplicationContext> threadLocal = new ThreadLocal<ApplicationContext>();

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		context = ac;
	}

	public static ConfigBean getConfig() {
		return context.getBean("config", ConfigBean.class);
	}

	public static WebBrowserHistoryManager getHistoryManager() {
		return context.getBean("webBrowserHistoryManager", WebBrowserHistoryManager.class);
	}

	public static PreAuthenticationService getPreAuthService() {
		return context.getBean("preAuthenticationService", PreAuthenticationService.class);
	}

	public static PortletAuthenticationService getPortletAuthService() {
		return context.getBean("portletAuthenticationService", PortletAuthenticationService.class);
	}

	public static BookServiceProxyBean getBookServiceBean() {
		return context.getBean("bookServiceBean", BookServiceProxyBean.class);
	}

	public static Cache getPortletSessionCache() {
		return context.getBean("portletSessionCache", Cache.class);
	}

	public static ApplicationContext getThreadApplicationContext() {
		return threadLocal.get();
	}

	public static void setThreadApplicationContext(ApplicationContext applicationContext) {
		threadLocal.set(applicationContext);
	}

	public static JNDIBean getJNDIBean() {
		try {
			return context.getBean("jndiBean", JNDIBean.class);
		} catch (BeansException e) {
			return null;
		}
	}

}
