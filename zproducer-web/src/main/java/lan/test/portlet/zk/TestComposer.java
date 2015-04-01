
package lan.test.portlet.zk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * TODO: comment
 * @author lazarev_nv 20.06.2013   14:52
 */
public class TestComposer extends SelectorComposer {
	@Wire
	org.zkoss.zul.Button btn;
	private EventQueue eq;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	@Listen("onClick = button#btn")
	public void changeLabel() {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();

		Object response = Executions.getCurrent().getNativeResponse();
		//response.setRenderParameter("location", book.getIsbnNumber().toString());
		PortletRequest portletRequest = (PortletRequest) request.getAttribute("javax.portlet.request");
		btn.setLabel("clicked");
	}
}