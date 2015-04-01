package lan.test.portlet.zk;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * TODO: comment
 * @author lazarev_nv 26.06.2013   16:19
 */
@XmlRootElement
public class TestEvent implements Serializable {
	private String value;
	private String description;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
