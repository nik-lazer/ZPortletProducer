package lan.test.portlet.zk.component.fileupload;

import org.apache.commons.fileupload.FileItem;
import org.zkoss.zk.ui.WebApp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface FileUploadStore {
	Integer getMaxFileSize(String uploadComponentId, String desktopId, WebApp webApp, HttpSession httpSession);

	void put(String desktopId, String contentId, ServletContext servletContext, HttpSession httpSession, List<FileItem> items);

	void setMaxFileSize(String uploadComponentId, String desktopId, WebApp webApp, Object nativeSession, Integer maxFileSize);

	void remove(String desktopId, String contenId, WebApp webApp, Object nativeSession);

	List<FileItem> get(String desktopId, String contenId, WebApp webApp, Object nativeSession);
}
