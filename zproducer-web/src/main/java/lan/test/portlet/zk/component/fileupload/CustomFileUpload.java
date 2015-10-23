package lan.test.portlet.zk.component.fileupload;

import lan.test.portlet.zk.wsrp.encoder.WebcenterPortletURLEncoder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.sys.ContentRenderer;
import org.zkoss.zk.ui.util.UiLifeCycle;
import org.zkoss.zul.Messagebox;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.fluentjava.FluentUtils.cast;

/**
 * @author nik-lazer  19.10.2015   16:44
 */
public class CustomFileUpload extends HtmlBasedComponent implements UiLifeCycle, AfterCompose, FileUploadSpecification {
		private static final Logger log = LoggerFactory.getLogger(CustomFileUpload.class);
		public static final String ON_FILE_ADD = "onFileAdd";
		public static final String ON_FILE_UPLOAD = "onFileUpload";
		public static final String ON_FILE_DELETE = "onFileDelete";
		public static final String ON_FILES_CLEAR = "onFilesClear";

		private List<FileItem> files = newArrayList();

		String url = WebcenterPortletURLEncoder.encodeActionURL("/upload");

		// default true
		boolean multiple;

		Integer maxFileSize;
		Integer maxNumberOfFiles;
		Set<String> extensionsSet;

		// default true
		boolean showProgress = true;
		// default true
		boolean showFileList = true;
		// default true
		boolean showDropzone = true;

		boolean isAllowedList = true;

	public String getSessionId() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		String sessionId = httpServletRequest.getSession().getId();
		return sessionId;
	}

	static {
		addClientEvent(CustomFileUpload.class, ON_FILE_ADD, CE_IMPORTANT);
		addClientEvent(CustomFileUpload.class, ON_FILE_UPLOAD, CE_IMPORTANT);
		addClientEvent(CustomFileUpload.class, ON_FILE_DELETE, CE_IMPORTANT);
		addClientEvent(CustomFileUpload.class, ON_FILES_CLEAR, CE_IMPORTANT);
	}

	public CustomFileUpload() {
	}

	@Override
	public void afterCompose() {
		getDesktop().addListener(this);
	}

	public List<FileItem> getResult() {
		return files;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if (this.url != url) {
			this.url = url;
			smartUpdate("url", url);
		}
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		if (this.multiple != multiple) {
			this.multiple = multiple;
			smartUpdate("multiple", multiple ? "true" : "false");
		}
	}

	public boolean isShowProgress() {
		return showProgress;
	}

	public void setShowProgress(boolean showProgress) {
		if (this.showProgress != showProgress) {
			this.showProgress = showProgress;
			smartUpdate("showProgress", showProgress ? "true" : "false");
		}
	}

	public boolean isShowFileList() {
		return showFileList;
	}

	public void setShowFileList(boolean showFileList) {
		if (this.showFileList != showFileList) {
			this.showFileList = showFileList;
			smartUpdate("showFileList", showFileList ? "true" : "false");
		}
	}

	public boolean isShowDropzone() {
		return showDropzone;
	}

	public void setShowDropzone(boolean showDropzone) {
		if (this.showDropzone != showDropzone) {
			this.showDropzone = showDropzone;
			smartUpdate("showDropzone", showDropzone ? "true" : "false");
		}
	}

	public boolean isAllowedList() {
		return isAllowedList;
	}

	public void setAllowedList(boolean isAllowedList) {
		if (this.isAllowedList != isAllowedList) {
			this.isAllowedList = isAllowedList;
			smartUpdate("isAllowedList", isAllowedList ? "true" : "false");
		}
	}

	public Integer getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(Integer maxFileSize) {
		if (this.maxFileSize != maxFileSize) {
			this.maxFileSize = maxFileSize;
			smartUpdate("maxFileSize", maxFileSize);
			FileUploadStoreLocator.getInstance().getStore().setMaxFileSize(getUuid(), getDesktop().getId(),
					Executions.getCurrent().getSession().getWebApp(),
					Executions.getCurrent().getSession().getNativeSession(),
					maxFileSize);
			getDesktop().setAttribute(MAX_FILE_SIZE_PREFIX + getUuid(), maxFileSize);
		}
	}

	public Integer getMaxNumberOfFiles() {
		return maxNumberOfFiles;
	}

	public void setMaxNumberOfFiles(Integer maxNumberOfFiles) {
		if (this.maxNumberOfFiles != maxNumberOfFiles) {
			this.maxNumberOfFiles = maxNumberOfFiles;
			smartUpdate("maxNumberOfFiles", maxNumberOfFiles);
		}
	}

	public Set<String> getExtensionsSet() {
		return extensionsSet;
	}

	public void setExtensionsSet(Set<String> extensionsSet) {
		if (!ObjectUtils.equals(this.extensionsSet, extensionsSet)) {
			this.extensionsSet = extensionsSet;
			smartUpdate("extensionsSet", getExtensionsSetString());
		}
	}

	public String getExtensionsSetString() {
		StringBuilder sb = new StringBuilder();
		if (extensionsSet == null || extensionsSet.isEmpty() || extensionsSet.contains("*")) {
			return "";
		}
		for (String ext : extensionsSet) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(ext);
		}

		return sb.toString();
	}

	private void updateTotalCount() {
		smartUpdate("totalCount", files.size());
	}

	@Override
	protected void renderProperties(ContentRenderer renderer) throws IOException {
		super.renderProperties(renderer);
		render(renderer, "url", url);
		render(renderer, "multiple", multiple ? "true" : "false");
		render(renderer, "maxFileSize", maxFileSize);
		render(renderer, "maxNumberOfFiles", maxNumberOfFiles);
		render(renderer, "showFileList", showFileList ? "true" : "false");
		render(renderer, "showProgress", showProgress ? "true" : "false");
		render(renderer, "totalCount", files.size());
		render(renderer, "extensionsSet", getExtensionsSetString());
		render(renderer, "isAllowedList", isAllowedList ? "true" : "false");
		render(renderer, "showDropzone", showDropzone ? "true" : "false");
	}

	@Override
	public void service(org.zkoss.zk.au.AuRequest request, boolean everError) {
		final String cmd = request.getCommand();

		if (cmd.equals(ON_FILE_ADD)) {
			try {
				fireEvent(ON_FILE_ADD, request.getData());
			} catch (Exception e) {

			}
		}
		if (cmd.equals(ON_FILE_DELETE)) {
			String fileName = (String) request.getData().get("fileName");
			Integer fileSize = (Integer) request.getData().get("fileSize");
			for (FileItem uploadedFile : files) {
				if (uploadedFile.getName().equals(fileName)) {
					if (uploadedFile.getSize() == fileSize) {
						files.remove(uploadedFile);
						updateTotalCount();
						break;
					}
				}
			}
		}
		if (cmd.equals(ON_FILES_CLEAR)) {
			files.clear();
			updateTotalCount();
			try {
				fireEvent(ON_FILES_CLEAR, request.getData());
			} catch (Exception e) {
			}
		}

		if (cmd.equals(ON_FILE_UPLOAD)) {
			Desktop desktop = getDesktop();
			if (request.getData().isEmpty()) {
				return;
			}
			JSONObject result = cast(request.getData());
			if (result != null) {
				JSONArray items = cast(result.values().iterator().next());
				Iterator iter = items.iterator();
				String contenId;

				if (!iter.hasNext()) {
					return;
				}
				do {
					JSONObject item = cast(iter.next());
					contenId = cast(item.get(CONTEN_ID_KEY));
				} while (iter.hasNext() && isEmpty(contenId));

				if (maxNumberOfFiles != null && files.size() >= maxNumberOfFiles) {
					Messagebox.show("Maximum number of files is" + maxNumberOfFiles);
					FileUploadStoreLocator.getInstance().getStore().remove(getDesktop().getId(), contenId,
							Executions.getCurrent().getSession().getWebApp(),
							Executions.getCurrent().getSession().getNativeSession());
					return;
				}

				List<FileItem> uploaded = FileUploadStoreLocator.getInstance().getStore().get(getDesktop().getId(), contenId,
						Executions.getCurrent().getSession().getWebApp(),
						Executions.getCurrent().getSession().getNativeSession());
				for (FileItem item : uploaded) {
					if (notAccepted(item.getName(), extensionsSet, isAllowedList)) {
						if (isAllowedList) {
							Messagebox.show("Is not allowed");
						} else {
							Messagebox.show(getExtensionsSetString());
						}
						FileUploadStoreLocator.getInstance().getStore().remove(getDesktop().getId(), contenId,
								Executions.getCurrent().getSession().getWebApp(),
								Executions.getCurrent().getSession().getNativeSession());
						return;
					}
				}

				files.addAll(uploaded);
				updateTotalCount();
				FileUploadStoreLocator.getInstance().getStore().remove(getDesktop().getId(), contenId,
						Executions.getCurrent().getSession().getWebApp(),
						Executions.getCurrent().getSession().getNativeSession());

				try {
					fireEvent(ON_FILE_UPLOAD, uploaded);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
			///Events.postEvent(evt);
		} else {
			super.service(request, everError);
		}
	}

	private boolean notAccepted(String fileName, Collection<String> extList, boolean isAllowedList) {
		if (extList == null || extList.isEmpty() || extList.contains("*")) {
			return false;
		}
		fileName = fileName.toLowerCase();
		for (String ext : extList) {
			if (fileName.endsWith(ext.toLowerCase())) {
				return !isAllowedList;
			}
		}
		return isAllowedList;
	}

	protected void fireEvent(String evtName, Object data) throws Exception {
		for (EventListener listener : getEventListeners(evtName)) {
			listener.onEvent(new Event(evtName, null, data));
		}
	}

	protected void destroy(Desktop desktop) {
		desktop.removeAttribute(MAX_FILE_SIZE_PREFIX + getUuid());
		desktop.removeListener(this);
	}

	@Override
	public void detach() {
		destroy(getDesktop());
		super.detach();
	}

	@Override
	public void afterComponentAttached(Component comp, Page page) {
	}

	@Override
	public void afterComponentDetached(Component comp, Page prevpage) {
		if (comp.equals(getParent())) {
			destroy(prevpage.getDesktop());
		}
	}

	@Override
	public void afterComponentMoved(Component parent, Component child, Component prevparent) {
	}

	@Override
	public void afterPageAttached(Page page, Desktop desktop) {
	}

	@Override
	public void afterPageDetached(Page page, Desktop prevdesktop) {
	}
}
