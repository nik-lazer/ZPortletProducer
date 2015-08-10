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
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Valve for tomcat - put any user name to getRemoteUser()
 * @author nik-lazer  06.08.2015   08:35
 */
public class RemoteUserValve extends ValveBase {
	private static final Log log = LogFactory.getLog(RemoteUserValve.class);

	protected String context;
	protected String token;
	protected String type = "header";

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
					log.info("RUV: user " + username + " setted for request " + request.getRequestURI());
				} else {
					log.warn("RUV: user not found for request " + request.getRequestURI());
				}
			} else {
				log.debug("RUV: remote user exists=" + request.getRemoteUser());
			}
		}
		getNext().invoke(request, response);
	}

	private void logRequestInfo(Request request) {
		log.trace("RUV: URI=" + request.getRequestURI());
		log.trace("RUV: URL=" + request.getRequestURL());
		log.trace("RUV: remoteUser" + request.getRemoteUser());
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String headerName = headers.nextElement();
			log.trace("RUV: header: " + headerName + "=" + request.getHeader(headerName));
		}
		List<Cookie> cookieList = Arrays.asList(request.getCookies());
		for (Cookie cookie: cookieList) {
			log.trace("RUV: cookie: " + cookie.getName() + "=" + cookie.getValue());
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
					log.debug("RUV: user founded in header[" + token + "]" + userName);
					return userName;
				}
			} else {
				List<Cookie> cookies = Arrays.asList(request.getCookies());
				for (Cookie cookie: cookies) {
					if (cookie.getName().equals(token)) {
						log.debug("RUV: user founded in header[" + cookie.getName() + "]" + cookie.getValue());
						return cookie.getValue();
					}
				}
			}
		}
		return getUserName(request, "cookie", "userName");
	}

	private String getUserName(Request request, String atype, String atoken) {
		if (atoken != null) {
			if (!"cookie".equals(atype)) {
				String userName = request.getHeader(atoken);
				log.debug("RUV: userName founded in header[" + atoken + "]" + userName);
				return userName;
			} else {
				List<Cookie> cookies = Arrays.asList(request.getCookies());
				for (Cookie cookie: cookies) {
					if (cookie.getName().equals(atoken)) {
						log.debug("RUV: userName founded in cookie[" + cookie.getName() + "]" + cookie.getValue());
						return cookie.getValue();
					}
				}
			}
		}
		return null;
	}

	private boolean isCookie() {
		return (type != null) && (type.equals("cookie"));
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
	}}
