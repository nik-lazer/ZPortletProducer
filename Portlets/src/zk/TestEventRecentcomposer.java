package zk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;

import java.util.Calendar;
import java.util.Date;

import org.zkoss.zul.Messagebox;

/**
 * TODO: comment
 * @author lazarev_nv 25.06.2013   17:30
 */
public class TestEventRecentcomposer extends SelectorComposer {

	private EventQueue eq;
	@Wire
	Label label;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		eq = EventQueues.lookup("testevent", EventQueues.APPLICATION, true);
		eq.subscribe(new EventListener() {
			public void onEvent(Event event) throws Exception {
				String value = (String)event.getData();
			    Messagebox.show("Event: "+value, "Error", Messagebox.OK, Messagebox.ERROR);
				label.setValue(value);
			}
		});
	}

}
