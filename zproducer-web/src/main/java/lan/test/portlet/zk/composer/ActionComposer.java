package lan.test.portlet.zk.composer;

import lan.test.portlet.zk.wsrp.session.DualSimpleSession;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import javax.portlet.PortletSession;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Composer for testing sessions in liferay WSRP portlet
 * @author nik-lazer  10.08.2015   10:59
 */
public class ActionComposer extends SelectorComposer<Window> {
	@Wire
	private Label actionLabel;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		Session session = Executions.getCurrent().getSession();
		Date date = (Date) session.getAttribute("action.time");
		if (date != null) {
			actionLabel.setValue(date.toString());
		}
	}
}
