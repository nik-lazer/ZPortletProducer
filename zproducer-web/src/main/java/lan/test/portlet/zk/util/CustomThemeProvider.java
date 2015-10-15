package lan.test.portlet.zk.util;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zkex.theme.StandardThemeProvider;

import java.util.Collection;
import java.util.List;

/**
 * Theme provider
 * @author nik-lazer  14.10.2015   17:18
 */
public class CustomThemeProvider extends StandardThemeProvider {
	public Collection getThemeURIs(Execution exec, List uris) {
		super.getThemeURIs(exec, uris);
		uris.add("~./custom/override.custom.css.dsp");
		return uris;
	}

}
