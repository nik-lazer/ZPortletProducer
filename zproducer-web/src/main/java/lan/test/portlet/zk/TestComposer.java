
package lan.test.portlet.zk;

import lan.test.portlet.zk.history.WebBrowserHistoryManager;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Composer for hello.zul
 * @author nik-lazer 20.06.2013   14:52
 */
public class TestComposer extends SelectorComposer {
	@Wire
	Button btn;
	@Wire
	Button histButton;
	@Wire
	Div div;
	@Wire
	Image picture;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	@Listen("onClick = button#btn")
	public void changeLabel() {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();

		Object response = Executions.getCurrent().getNativeResponse();
		PortletRequest portletRequest = (PortletRequest) request.getAttribute("javax.portlet.request");
		btn.setLabel("clicked");
		picture.setSrc("/1.jpg");
	}

	@Listen("onClick = button#histButton")
	public void badHistory() {
		WebBrowserHistoryManager.popUrlFromStackAndReplace();
	}
}