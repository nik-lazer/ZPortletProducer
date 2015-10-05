package lan.test.portlet.zk.component;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Window;

import java.util.HashMap;
import java.util.Map;

import static lan.test.portlet.zk.util.UIUtils.loadComponentFromZul;

/**
 * Facade for portlet which can send arguments to the composer
 * @author nik-lazer  05.10.2015   11:55
 */
public class PortletWindow extends Window {
	public PortletWindow() {
		addEventListener(Events.ON_CREATE, new SerializableEventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Map args = new HashMap();
				args.put("portletMode", true);
				Component main = loadComponentFromZul("/hello.zul", null, args);
				Page _page = PortletWindow.this.getPage();
				PortletWindow.this.detach();
				main.setPage(_page);
				_page.setTitle(getTitle());
				main.setVisible(true);
			}
		});
	}
}
