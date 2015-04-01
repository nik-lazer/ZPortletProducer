package lan.test.portlet.zk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO: comment
 * @author lazarev_nv 25.06.2013   17:29
 */
public class TestEventComposer extends SelectorComposer {
	private static final String DOC_EVENT_KEY = "docType";
	public static final String DOCTYPE_ZKR = "ZKR";
	public static final String DOCTYPE_RASHODNOE_RASPISANIE  = "RASHODNOE_RASPISANIE";
	public static final String DOCTYPE_MONEYORDER = "MONEYORDER";

	@Wire
	Button zkr;
	@Wire
	Button rasp;
	@Wire
	Button money;
	@Wire
	Label zkrLabel;
	@Wire
	Label raspLabel;
	@Wire
	Label moneyLabel;
	@Wire
	Label docTypeLabel;
	private EventQueue eq;
	private EventQueue eq1;
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		eq1 = EventQueues.lookup("portletevent", EventQueues.APPLICATION, true);
		eq1.subscribe(new EventListener() {
			public void onEvent(Event event) throws Exception {
				String value = (String)event.getData();
				docTypeLabel.setValue(value);
			}
		});

		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
//		String docTypeName = request.getParameter("docTypeName");
//		docTypeLabel = (Label)comp.getFellow("docTypeLabel");
//		if (docTypeName!=null) {
//			docTypeLabel.setValue(docTypeName);
//		}

	}

	@Listen("onClick = #zkr")
	public void changeZkrLabel() {
		publicEvent(DOC_EVENT_KEY, DOCTYPE_ZKR);
		zkrLabel.setValue("clicked:" + getTime());
	}

	@Listen("onClick = button#rasp")
	public void changeRaspLabel() {
		publicEvent(DOC_EVENT_KEY, DOCTYPE_RASHODNOE_RASPISANIE);
		raspLabel.setValue("clicked");
	}

	@Listen("onClick=button#money")
	public void changeMoneyLabel() {
		publicEvent(DOC_EVENT_KEY, DOCTYPE_MONEYORDER);
		moneyLabel.setValue("clicked");
	}

	private void publicEvent(String key, String value) {
		eq = EventQueues.lookup("testevent", EventQueues.APPLICATION, true);
		eq.publish(new Event(key, null, value));
	}

	private Date getTime() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

}
