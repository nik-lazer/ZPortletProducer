package lan.test.portlet.zk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.Button;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * TODO: comment
 * @author lazarev_nv 17.06.2013   17:00
 */
public class TestRecentComposer extends GenericComposer {

	private EventQueue eq;
	private EventQueue eq1;
	private Button button;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		button =  (Button)comp.getFellow("hibutton");
		button.setLabel("After composer");
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		PortletRequest portletRequest = (PortletRequest) request.getAttribute("javax.portlet.request");
		//if (portletRequest!=null) {
		eq1 = EventQueues.lookup("interactive", EventQueues.SESSION, true);
		eq1.subscribe(new EventListener() {
			public void onEvent(Event event) throws Exception {
				String value = (String)event.getData();
				button.setLabel("value="+value);
			}
		});
		String location = request.getParameter("location");
		if (location!=null) {
			eq = EventQueues.lookup("interactive", EventQueues.SESSION, false);
			eq.publish(new Event("location", null, location));
			//button.setLabel("location = "+location);
		}

	}}
