package lan.test.portlet.zk.component.fileupload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.mesg.Messages;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Window;
import org.zkoss.zul.mesg.MZul;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nik-lazer  19.10.2015   16:47
 */
public class FileuploadDlg extends Window implements AfterCompose {
	private static final Logger log = LoggerFactory.getLogger(FileuploadDlg.class);
	private static final String ZUL_TEMPLATE = "/fileupload/fileuploaddlg2.zul";
	private static final String ATTR_FILEUPLOAD_TARGET = "org.zkoss.zul.Fileupload.target";

	CustomFileUpload upload;
	protected SerializableEventListener<Event> windowResultListener;

	@Override
	public void afterCompose() {
		addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onClose(false);
			}
		});
		getFellow("okButton").addEventListener(Events.ON_CLICK, new SerializableEventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onClose(true);
			}
		});
		getFellow("cancelButton").addEventListener(Events.ON_CLICK, new SerializableEventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				onClose(false);
			}
		});
		upload = (CustomFileUpload) getFellow("fileupload");

	}

	public void onClose(boolean needStoreData) {

		if (needStoreData) {
			List<FileItem> dialogResult = upload.getResult();
			fireWindowResultEvent(dialogResult);
		}
		detach();
	}

	public static void get(String message, String title, int max, int maxsize, boolean alwaysNative,
	                       String[] allowedFileExtensions, SerializableEventListener uploadDoneListener) {
		String extensionList = StringUtils.join(allowedFileExtensions, "|");

		final Map<String, String> params = new HashMap<String, String>(8);
		params.put("message", message == null ? Messages.get(MZul.UPLOAD_MESSAGE) : message);
		params.put("title", title == null ? Messages.get(MZul.UPLOAD_TITLE) : title);
		params.put("max", String.valueOf(max == 0 ? 1 : max > 1000 ? 1000 : max < -1000 ? -1000 : max));
		params.put("useNative", String.valueOf(alwaysNative));
		params.put(FileUploadSpecification.PROP_MAX_FILE_SIZE, String.valueOf(maxsize));
		params.put(FileUploadSpecification.PROP_ALLOWED_FILE_EXTENSIONS, extensionList);

		final FileuploadDlg dlg = (FileuploadDlg) Executions.getCurrent().createComponents(ZUL_TEMPLATE, null, params);
		dlg.setWindowResultListener(uploadDoneListener);
		dlg.doHighlighted();
	}

	public SerializableEventListener getWindowResultListener() {
		return windowResultListener;
	}

	public void setWindowResultListener(SerializableEventListener<Event> windowResultListener) {
		this.windowResultListener = windowResultListener;
	}

	public void fireWindowResultEvent() {
		if (windowResultListener == null) {
			return;
		}

		try {
			windowResultListener.onEvent(new Event("NonModalWindowEvent", this));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public void fireWindowResultEvent(List<FileItem> data) {
		if (windowResultListener == null) {
			return;
		}

		try {
			windowResultListener.onEvent(new Event("NonModalWindowEvent", this, data));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}


}
