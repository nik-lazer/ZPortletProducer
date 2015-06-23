package lan.test.config;

import javax.annotation.Resource;

/**
 * @author nik-lazer  18.06.2015   16:31
 */
public class JNDIBean {
	private ResourceConfigBean resourceConfigBean;

	public ResourceConfigBean getResourceConfigBean() {
		return resourceConfigBean;
	}

	public void setResourceConfigBean(ResourceConfigBean resourceConfigBean) {
		this.resourceConfigBean = resourceConfigBean;
	}
}
