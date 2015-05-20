package lan.test.config;

import lan.test.portlet.zk.history.WebBrowserHistoryManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Application context provider
 * @author nik-lazer  20.05.2015   10:19
 */
public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext context;

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

}
