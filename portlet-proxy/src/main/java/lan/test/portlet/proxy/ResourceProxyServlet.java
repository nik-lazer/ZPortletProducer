package lan.test.portlet.proxy;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Proxy servlet for resources. It used in servlet mode.
 * @author nik-lazer  28.04.2015   15:46
 */
public class ResourceProxyServlet extends HttpServlet {
	private String url;
	private static final Log log = LogFactory.getLog(ResourceProxyServlet.class);
	private static final String ZK_SESSION_KEY = "zksession";

	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		url = getTargetContextPath();
	}

	private String getTargetUrl(HttpServletRequest request) throws MalformedURLException {
		String currentContextPath = request.getContextPath() + "/resourceproxy/cache";
		StringBuffer uri = request.getRequestURL();
		URL url = new URL(uri.toString());
		String path = url.getPath().replaceFirst(currentContextPath, getTargetContextPath());
		URL newUrl = new URL(url.getProtocol(), url.getHost(), url.getPort(), path);
		return newUrl.toString();
	}

	private String getTargetContextPath() {
		return "/zproducer";
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		if (url == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String uri = getTargetUrl(request);
		HttpGet proxyMethod = new HttpGet(uri);
		String ses = request.getParameter("ses");
		if (ses != null && !ses.isEmpty()) {
			setSessionId(proxyMethod, ses);
		}
		prepareRequest(request, response, proxyMethod);
		sendRequest(request, response, proxyMethod);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		if (url == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String uri = getTargetUrl(request);
		HttpPost httpPost = new HttpPost(uri);
		prepareRequest(request, response, httpPost);
		fillPostRequest(request, response, httpPost);
		sendRequest(request, response, httpPost);
	}

	private void prepareRequest(HttpServletRequest request, HttpServletResponse response, HttpUriRequest httpUriRequest) throws IOException{
		Enumeration<String> requestHeaders = request.getHeaderNames();
		while (requestHeaders.hasMoreElements()) {
			String name = requestHeaders.nextElement();
			if (!name.equals("Cookie")) {
				httpUriRequest.setHeader(name, request.getHeader(name));
			}
		}
		httpUriRequest.removeHeaders(HTTP.TRANSFER_ENCODING);
		httpUriRequest.removeHeaders(HTTP.CONTENT_LEN);
	}

	private void fillPostRequest(HttpServletRequest request, HttpServletResponse response, HttpPost httpPost) throws IOException{
		if (ServletFileUpload.isMultipartContent(request)) {
			httpPost.removeHeaders(HTTP.CONTENT_TYPE);
			handleMultiPart(request, httpPost);
		} else {
			String ses = request.getParameter("ses");
			if (ses != null && !ses.isEmpty()) {
				setSessionId(httpPost, ses);
			}
			Enumeration paramNames = request.getParameterNames();
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			while (paramNames.hasMoreElements()) {
				String name = paramNames.nextElement().toString();
				if (!"ses".equals(name)) {
					urlParameters.add(new BasicNameValuePair(name, request.getParameter(name)));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
		}
	}

	private void sendRequest(HttpServletRequest request, HttpServletResponse response, HttpUriRequest httpUriRequest) throws IOException{
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = client.execute(httpUriRequest);
		org.apache.http.Header[] responseHeaders = httpResponse.getAllHeaders();
		for (int i=0; i<responseHeaders.length; i++) {
			Header header = responseHeaders[i];
			response.setHeader(header.getName(), header.getValue());
		}
		response.setStatus(httpResponse.getStatusLine().getStatusCode());
		copyRequestBody(httpResponse, response);
		httpResponse.close();
		client.close();
	}

	private void copyRequestBody(CloseableHttpResponse httpResponse, HttpServletResponse response) throws IOException {
		if (httpResponse.getEntity() != null) {
			InputStream responseBodyAsStream = httpResponse.getEntity().getContent();
			if (responseBodyAsStream != null) {
				IOUtils.copy(responseBodyAsStream, response.getOutputStream());
				IOUtils.closeQuietly(responseBodyAsStream);
				IOUtils.closeQuietly(response.getOutputStream());
			}
		}
	}

	private void handleMultiPart(HttpServletRequest request, HttpPost httpPost) {
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item: items) {
				if (!item.isFormField()) {
					ByteArrayBody byteArrayBody = new ByteArrayBody(item.get(), item.getName());
					entity.addPart(item.getFieldName(), byteArrayBody);
				} else {
					try {
						StringBody stringBody = new StringBody(item.getString());
						entity.addPart(item.getFieldName(), stringBody);
					} catch (UnsupportedEncodingException e) {
						log.error(e.getMessage(), e);
					}
					if (item.getFieldName().equals(ZK_SESSION_KEY)) {
						setSessionId(httpPost, item.getString());
					}
				}
			}
		} catch (FileUploadException e) {
			log.error(e.getMessage(), e);
		}
		httpPost.setEntity(entity);
	}

	@Override
	public String getServletInfo() {
		return "Resource Proxy Servlet";
	}

	private void setSessionId(HttpUriRequest httpMethod, String sessionId) {
		httpMethod.setHeader("Cookie", "JSESSIONID=" + sessionId);
	}

}
