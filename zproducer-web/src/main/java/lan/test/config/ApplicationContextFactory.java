package lan.test.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Factory for beans
 * @author nik-lazer  18.05.2015   17:25
 */
public class ApplicationContextFactory {
	private static ApplicationContext context;

	private static ApplicationContext getContext() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		return context;
	}

	public static ConfigBean getConfig() {
		return getContext().getBean("config", ConfigBean.class);
	}
}
