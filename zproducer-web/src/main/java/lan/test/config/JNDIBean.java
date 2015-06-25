package lan.test.config;

import javax.annotation.Resource;

/**
 * @author nik-lazer  18.06.2015   16:31
 */
public class JNDIBean {
	private ResourceConfigBean resourceConfigBean;
	private String homeDir;

	public ResourceConfigBean getResourceConfigBean() {
		return resourceConfigBean;
	}

	public void setResourceConfigBean(ResourceConfigBean resourceConfigBean) {
		this.resourceConfigBean = resourceConfigBean;
	}

	public String getHomeDir() {
		return homeDir;
	}

	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}
}
