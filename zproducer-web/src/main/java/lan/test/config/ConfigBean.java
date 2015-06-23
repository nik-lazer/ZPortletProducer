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
	@Value("${resources.context.prefix:Application1-Portal-context-root}")
	private String resContextPrefix;
	@Value("${resources.context.suffix:/resourceproxy/cache/portlets/resources}")
	private String resContextSuffix;
	@Value("${resources.portlet.keyword:adfportlet}")
	private String resPortletKeyword;
	@Value("${history.needRewriteUrl:false}")
	private Boolean needRewriteUrl;

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

	public Boolean isNeedRewriteUrl() {
		return needRewriteUrl;
	}

	public void setNeedRewriteUrl(Boolean needRewriteUrl) {
		this.needRewriteUrl = needRewriteUrl;
	}
}
