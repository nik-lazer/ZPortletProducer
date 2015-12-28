package lan.test.lock.servlet;

import lan.test.lock.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Displays lock state
 * @author nik-lazer  23.12.2015   15:46
 */
public class LockServlet extends HttpServlet {
	@Autowired
	LockService lockService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mode = req.getParameter("mode");
		PrintWriter writer = resp.getWriter();
		if ((mode != null) && (!mode.isEmpty())) {
			if (mode.equals("lock")) {
				lockService.lock();
				writer.println("Locked succesfully");
			} else if (mode.equals("unlock")) {
				lockService.unlock();
				writer.println("Unlocked succesfully");
			}
		}
		writer.println("Lock service is " + (lockService.isLocked() ? "locked" : "unlocked"));
	}

	public void setLockService(LockService lockService) {
		this.lockService = lockService;
	}
}
