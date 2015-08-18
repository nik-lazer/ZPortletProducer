package lan.tomcat.valve;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.valves.ValveBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Valve for tomcat - put any user name to getRemoteUser()
 * @author nik-lazer  06.08.2015   08:35
 */
public class RemoteUserValve extends ValveBase {
	private static final Logger log = LoggerFactory.getLogger(RemoteUserValve.class);

	protected String context;
	protected String token;
	protected String type = HEADER_TYPE;
	private static final String HEADER_TYPE = "header";
	private static final String COOKIE_TYPE = "cookie";

	public RemoteUserValve() {
	}

	@Override
	public void invoke(final Request request, final Response response)
			throws IOException, ServletException {
		if (isFillRequest(request)) {
			if (log.isTraceEnabled()) {
				logRequestInfo(request);
			}
			if (request.getRemoteUser() == null) {
				final String username = getUserName(request);
				if (username != null) {
					final String credentials = "credentials";
					final List<String> roles = new ArrayList<String>();

					// Tomcat 7 version
					final Principal principal = new GenericPrincipal(username,
							credentials, roles);

					request.setUserPrincipal(principal);
					log.debug("User {} setted for request {}", username, request.getRequestURI());
				} else {
					log.warn("User not found for request {}", request.getRequestURI());
				}
			} else {
				log.debug("Remote user exists={}", request.getRemoteUser());
			}
		}
		getNext().invoke(request, response);
	}

	private void logRequestInfo(Request request) {
		log.trace("URI={}", request.getRequestURI());
		log.trace("URL={}", request.getRequestURL());
		log.trace("remoteUser {}", request.getRemoteUser());
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String headerName = headers.nextElement();
			log.trace("Header: {}={}", headerName, request.getHeader(headerName));
		}
		List<Cookie> cookieList = Arrays.asList(request.getCookies());
		for (Cookie cookie: cookieList) {
			log.trace("Cookie: {}={}", cookie.getName(), cookie.getValue());
		}
	}

	private boolean isFillRequest(final Request request) {
		return (context == null) || ((context != null) && (context.equals(request.getContextPath())));
	}

	private String getUserName(Request request) {
		if (token != null) {
			if (!isCookie()) {
				String userName = request.getHeader(token);
				if (userName != null)  {
					log.debug("User founded in header[{}] {}", token, userName);
					return userName;
				}
			} else {
				List<Cookie> cookies = Arrays.asList(request.getCookies());
				for (Cookie cookie: cookies) {
					if (cookie.getName().equals(token)) {
						log.debug("User founded in header[{}] {}" ,cookie.getName(), cookie.getValue());
						return cookie.getValue();
					}
				}
			}
		}
		return null;
	}

	private boolean isCookie() {
		return (type != null) && (type.equals(COOKIE_TYPE));
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
