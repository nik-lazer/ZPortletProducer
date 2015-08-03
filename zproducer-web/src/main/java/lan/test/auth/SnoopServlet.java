package lan.test.auth;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Displays information about request and environment
 * @author nik-lazer  03.08.2015   10:46
 */
public class SnoopServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		writer.println("<html>");

		writer.println("<header>");
		writer.println("<title>Snoop Servlet (zproducer)</title>");
		writer.println("</header>");
		writer.println("<body>");
		writer.println("<h1>Snoop Servlet - Request/Client Information</h1>");

		writer.println("<h1>Requested URL:</h1>");
		writer.println("<table border='1'>");
		writer.println("<tr><td>" + req.getRequestURL() + "</td></tr>");
		writer.println("</table>");

		writer.println("<h1>Servlet Name:</h1>");
		writer.println("<table  border='1'>");
		writer.println("<tr><td>" + getServletConfig().getServletName() + "</td></tr>");
		writer.println("</table>");

		writer.println("<h1>Request Information:</h1>");
		writer.println("<table  border='1'>");
		writer.println("<tr>");
		writer.println("<td>Request method</td>");
		writer.println("<td>" + req.getMethod() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Request URI</td>");
		writer.println("<td>" + req.getRequestURI() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Request protocol</td>");
		writer.println("<td>" + req.getProtocol() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Servlet path</td>");
		writer.println("<td>" + req.getServletPath() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Path info</td>");
		writer.println("<td>" + req.getPathInfo() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Path translated</td>");
		writer.println("<td>" + req.getPathTranslated() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Character encoding</td>");
		writer.println("<td>" + req.getCharacterEncoding() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Query string</td>");
		writer.println("<td>" + req.getQueryString() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Content length</td>");
		writer.println("<td>" + req.getContentLength() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Content type</td>");
		writer.println("<td>" + req.getContentType() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Server name</td>");
		writer.println("<td>" + req.getServerName() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Server port</td>");
		writer.println("<td>" + req.getServerPort() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Remote user</td>");
		writer.println("<td>" + req.getRemoteUser() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Remote address</td>");
		writer.println("<td>" + req.getRemoteAddr() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Remote host</td>");
		writer.println("<td>" + req.getRemoteHost() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Remote port</td>");
		writer.println("<td>" + req.getRemotePort() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Local address</td>");
		writer.println("<td>" + req.getLocalAddr() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Local host</td>");
		writer.println("<td>" + req.getLocalName() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Local port</td>");
		writer.println("<td>" + req.getLocalPort() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Authorization scheme</td>");
		writer.println("<td>" + req.getAuthType() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>Preferred Client Locale</td>");
		writer.println("<td>" + req.getLocale() + "</td>");
		writer.println("</tr>");
		Enumeration locales = req.getLocales();
		while (locales.hasMoreElements()) {
			writer.println("<tr>");
			writer.println("<td>All Client Locales</td>");
			writer.println("<td>" + locales.nextElement() + "</td>");
			writer.println("</tr>");
		}
		writer.println("<tr>");
		writer.println("<td>Context Path</td>");
		writer.println("<td>" + req.getContextPath() + "</td>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>User Principal</td>");
		writer.println("<td>" + req.getUserPrincipal() + "</td>");
		writer.println("</tr>");
		writer.println("</table>");

		writer.println("<h1>Request headers:</h1>");
		Enumeration headerNames = req.getHeaderNames();
		writer.println("<table  border='1'>");
		while (headerNames.hasMoreElements()) {
			writer.println("<tr>");
			String header = (String) headerNames.nextElement();
			writer.println("<td>" + header + "</td>");
			writer.println("<td>" + req.getHeader(header) + "</td>");
			writer.println("</tr>");
		}
		writer.println("</table>");

		writer.println("<h1>Client cookies</h1>");
		List<Cookie> cookies = Arrays.asList(req.getCookies());
		writer.println("<table  border='1'>");
		for (Cookie cookie: cookies) {
			writer.println("<tr>");
			writer.println("<td>" + cookie.getName() + "</td>");
			writer.println("<td>" + cookie.getValue() + "</td>");
			writer.println("</tr>");
		}
		writer.println("</table>");

		writer.println("<h1>Request attributes:</h1>");
		Enumeration attributeNames = req.getAttributeNames();
		writer.println("<table  border='1'>");
		while (attributeNames.hasMoreElements()) {
			writer.println("<tr>");
			String attributeName = (String) attributeNames.nextElement();
			writer.println("<td>" + attributeName + "</td>");
			writer.println("<td>" + req.getAttribute(attributeName) + "</td>");
			writer.println("</tr>");
		}
		writer.println("</table>");

		writer.println("<h1>ServletContext attributes:</h1>");
		Enumeration servletContextAttributeNames = getServletContext().getAttributeNames();
		writer.println("<table  border='1'>");
		while (servletContextAttributeNames.hasMoreElements()) {
			writer.println("<tr>");
			String attributeName = (String) servletContextAttributeNames.nextElement();
			writer.println("<td>" + attributeName + "</td>");
			writer.println("<td>" + getServletContext().getAttribute(attributeName) + "</td>");
			writer.println("</tr>");
		}
		writer.println("</table>");
		writer.println("</body>");
		writer.println("</html>");

	}
}
