package lan.test.portlet.zk.composer;

import lan.test.config.ApplicationContextProvider;
import lan.test.portlet.zk.history.WebBrowserHistoryManager;
import lan.test.portlet.zk.util.UIUtils;
import lan.test.portlet.zk.wsrp.session.DualSimpleSession;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import static lan.test.portlet.zk.util.UIUtils.resolveFileName;

/**
 * Composer for hello.zul
 * @author nik-lazer 20.06.2013   14:52
 */
public class TestComposer extends SelectorComposer {
	@Wire
	private Button btn;
	@Wire
	private Button histButton;
	@Wire
	private Button downloadButton;
	@Wire
	private Div div;
	@Wire
	private Image picture;
	@Wire
	private Label helloLabel;

	private WebBrowserHistoryManager webBrowserHistoryManager = ApplicationContextProvider.getHistoryManager();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") != null) {
			helloLabel.setValue("Hello, " + session.getAttribute("currentUser"));
		}
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
		webBrowserHistoryManager.popUrlFromStackAndReplace();
	}

	@Listen("onClick = button#downloadButton")
	public void dowloadFile() {
		String text = "File to download";
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(text.getBytes("UTF-8"));
			Filedownload.save(inputStream, null, resolveFileName(Calendar.getInstance()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick = button#actionButton")
	public void action() {
		Session session = Sessions.getCurrent();
		session.setAttribute("action.time", new Date());
		Window window = UIUtils.loadComponentFromZul("/WEB-INF/zul/action.zul");
		window.setPosition("center");
		window.doModal();
	}
}