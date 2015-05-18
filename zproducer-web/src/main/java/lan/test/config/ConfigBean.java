package lan.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Bean for configuration parameters
 * @author nik-lazer  18.05.2015   17:18
 */
@Component("config")
public class ConfigBean {
	private String resContextPrefix;
	private String resContextSuffix;
	private String resPortletKeyword;
	private boolean needRewriteUrl;

	public String getResContextPrefix() {
		return resContextPrefix;
	}

	public void setResContextPrefix(String resContextPrefix) {
		this.resContextPrefix = resContextPrefix;
	}

	public String getResContextSuffix() {
		return resContextSuffix;
	}

	public void setResContextSuffix(String resContextSuffix) {
		this.resContextSuffix = resContextSuffix;
	}

	public String getResPortletKeyword() {
		return resPortletKeyword;
	}

	public void setResPortletKeyword(String resPortletKeyword) {
		this.resPortletKeyword = resPortletKeyword;
	}

	public boolean isNeedRewriteUrl() {
		return needRewriteUrl;
	}

	public void setNeedRewriteUrl(boolean needRewriteUrl) {
		this.needRewriteUrl = needRewriteUrl;
	}
}
