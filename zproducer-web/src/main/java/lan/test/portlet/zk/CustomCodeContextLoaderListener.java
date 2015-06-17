package lan.test.portlet.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicReference;

import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.split;

/**
 * Dummy custom code loader
 * @author nik-lazer  16.06.2015   17:25
 */
public class CustomCodeContextLoaderListener extends ContextLoaderListener implements HttpSessionListener {
	private static final Logger log = LoggerFactory.getLogger(CustomCodeContextLoaderListener.class);

	//shortlink for ContextLoadBarier.isContextReady
	private boolean contextStarted = false;

	//Public constructor is required by servlet specification
	public CustomCodeContextLoaderListener() {
	}

	@Override
	public void contextInitialized(final ServletContextEvent event) {
		final AtomicReference<ServletContextEvent> eventAtomicReference = new AtomicReference<ServletContextEvent>(event);
		final Thread currentThread = Thread.currentThread();
		currentThread.setName("MAIN"); //Main web context loader thread
		final ClassLoader parentWebClassLoader = currentThread.getContextClassLoader();

		log.info("Launching main context");

		final Runnable contextStarter = new Runnable() {
			@Override
			public void run() {
				try {
					CustomCodeContextLoaderListener.super.contextInitialized(eventAtomicReference.get());
					CustomCodeContextLoaderListener.this.contextStarted = true;
				} catch (BeansException be) {
					log.error("Context failed", be);
					try {
						CustomCodeContextLoaderListener.super.contextDestroyed(eventAtomicReference.get());
					} finally {
						log.error("Server stopped");
					}
				}
			}
		};

		final Thread loader = new Thread(contextStarter, "Main starting thread");
		loader.run();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

	}
}
