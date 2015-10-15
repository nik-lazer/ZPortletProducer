package lan.test.portlet;

import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppInit;
import org.zkoss.zul.theme.Themes;

public class CustomThemeListenerThemeWebAppInit implements WebAppInit {

	private final static String CUSTOM_NAME = "custom";
	private final static String CUSTOM_DISPLAY = "Custom";
	private final static int CUSTOM_PRIORITY = 1000;

	public void init(WebApp wapp) throws Exception {
		Themes.register(CUSTOM_NAME, CUSTOM_DISPLAY, CUSTOM_PRIORITY);
	}

}
