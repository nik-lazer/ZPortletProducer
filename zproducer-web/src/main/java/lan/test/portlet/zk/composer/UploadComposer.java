package lan.test.portlet.zk.composer;

import com.google.common.io.CharStreams;
import lan.test.portlet.zk.component.fileupload.CustomFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author nik-lazer  19.10.2015   14:42
 */
public class UploadComposer extends SelectorComposer<Window> {
	protected static final Logger log = LoggerFactory.getLogger(UploadComposer.class);
	@Wire
	private CustomFileUpload fileupload;
	@Wire
	private Textbox resultsBox;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		fileupload.addEventListener(CustomFileUpload.ON_FILE_UPLOAD, new SerializableEventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				onFileUpload(event);
			}
		});
		fileupload.setExtensionsSet(new HashSet<String>(Arrays.asList("txt")));
	}

	public void onFileUpload(Event event) {
		List<FileItem> result = fileupload.getResult();

		if (result == null || result.size() < 1) {
			return;
		}
		try {
			FileItem fileItem = result.get(0);
			Media media = new AMedia(fileItem.getName(), FilenameUtils.getExtension(fileItem.getName()), fileItem.getContentType(), fileItem.getInputStream());
			if (media != null) {
				if ("txt".equals(media.getFormat())) {
					String data = CharStreams.toString(new InputStreamReader(media.getStreamData()));
					resultsBox.setValue(data);
				} else {
					Messagebox.show("Wrong format: "+media.getContentType(), "Error", Messagebox.OK, Messagebox.ERROR);
				}
			} else {
				Messagebox.show("Not an text: "+media, "Error", Messagebox.OK, Messagebox.ERROR);
			}
		} catch (Exception ex) {
			log.error("Error processing uploaded file:" + ex, ex);
		}
	}

}
