package lan.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Bean for configuration parameters
 * @author nik-lazer  18.05.2015   17:18
 */
@Component("config")
public class ConfigBean {
	@Value("${history.needRewriteUrl:false}")
	private Boolean needRewriteUrl;

	private String homeDir;

	public Boolean isNeedRewriteUrl() {
		return needRewriteUrl;
	}

	public void setNeedRewriteUrl(Boolean needRewriteUrl) {
		this.needRewriteUrl = needRewriteUrl;
	}

	public String getHomeDir() {
		return homeDir;
	}

	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}
}
