package lan.test.portlet.zk.component.fileupload;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.WebApp;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GlobalFileUploadStore implements FileUploadStore {
	protected static final Logger log = LoggerFactory.getLogger(GlobalFileUploadStore.class);
	private static final String MAX_FILE_SIZE_KEY_PATTER = "%s-%s";
	private static final Cache<String, Integer> maxFileSize = CacheBuilder.newBuilder().expireAfterWrite(30L, TimeUnit.MINUTES).build();
	private static final Cache<String, List<FileItem>> fileCache = CacheBuilder.newBuilder().expireAfterWrite(30L, TimeUnit.MINUTES).build();

	@Override
	public Integer getMaxFileSize(String uploadComponentId, String desktopId, WebApp webApp, HttpSession httpSession) {
		return maxFileSize.getIfPresent(String.format(MAX_FILE_SIZE_KEY_PATTER, desktopId, uploadComponentId));
	}

	@Override
	public void put(String desktopId, String contentId, ServletContext servletContext, HttpSession httpSession, List<FileItem> items) {
		fileCache.put(contentId, items);
	}

	@Override
	public void setMaxFileSize(String uploadComponentId, String desktopId, WebApp webApp, Object nativeSession, Integer maxFileSize) {
		GlobalFileUploadStore.maxFileSize.put(String.format(MAX_FILE_SIZE_KEY_PATTER, desktopId, uploadComponentId), maxFileSize);
	}

	@Override
	public void remove(String desktopId, String contenId, WebApp webApp, Object nativeSession) {
		fileCache.invalidate(contenId);
	}

	@Override
	public List<FileItem> get(String desktopId, String contenId, WebApp webApp, Object nativeSession) {
		return fileCache.getIfPresent(contenId);
	}
}
