package lan.test.portlet.zk.wsrp.session;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lan.test.config.ApplicationContextProvider;

import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Helper for joining portlet session and http session in ajax
 * @author nik-lazer  11.08.2015   11:38
 */
public class DualSessionHelper {
	public static final String PORTLET_SESSION_ID = "PSI";

	public static PortletSession getPortletSession(String id) {
		PortletSession portletSess = (PortletSession) ApplicationContextProvider.getPortletSessionCache().getIfPresent(id);
		return portletSess;
	}

	public static void putPortletSession(String id, Object session) {
		ApplicationContextProvider.getPortletSessionCache().put(id, session);
	}

	public static String getPortletSessionId(HttpServletRequest httpServletRequest) {
		String portletSessionId = httpServletRequest.getParameter(PORTLET_SESSION_ID);
		return portletSessionId;
	}

	public Cache<Object, Object> initBuilder() {
		Cache<Object, Object> cache = CacheBuilder.newBuilder().expireAfterAccess(15, TimeUnit.MINUTES).build();
		return cache;
	}
}
