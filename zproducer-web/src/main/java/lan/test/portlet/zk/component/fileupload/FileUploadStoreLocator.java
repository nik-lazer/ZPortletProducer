package lan.test.portlet.zk.component.fileupload;

import com.google.common.base.Preconditions;

public class FileUploadStoreLocator {
	private static FileUploadStore fileUploadStore;
	private static FileUploadStoreLocator instance;

	private FileUploadStoreLocator(FileUploadStore fileUploadStore) {
		FileUploadStoreLocator.fileUploadStore = fileUploadStore;
	}

	public static FileUploadStoreLocator registerStoreInstance(FileUploadStore fileUploadStore) {
		return instance = new FileUploadStoreLocator(fileUploadStore);
	}

	public FileUploadStore getStore() {
		Preconditions.checkState(fileUploadStore != null, "FileUploadStoreLocator не был инициализирован");
		return fileUploadStore;
	}

	public static FileUploadStoreLocator getInstance() {
		Preconditions.checkState(instance != null, "FileUploadStoreLocator не был инициализирован");
		return instance;
	}
}
