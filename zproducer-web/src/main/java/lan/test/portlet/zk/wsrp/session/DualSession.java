package lan.test.portlet.zk.wsrp.session;

/**
 * Interface for dual sessions
 * @author nik-lazer  11.08.2015   18:02
 */
public interface DualSession {
	public String getPortletSessionId();
	public void setPortletSessionId(String portletSessionId);
}
