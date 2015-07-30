package lan.test.portlet.zk.wsrp;

import org.junit.Before;
import org.junit.Test;

import javax.portlet.PortletException;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link lan.test.portlet.zk.wsrp.WebcenterWsrpEncoderFixResourceFilter}
 * @author nik-lazer  30.07.2015   14:49
 */
public class WebcenterWsrpEncoderFixResourceFilterTest {
	private WebcenterWsrpEncoderFixResourceFilter resourceFilter;

	@Before
	public void init() {
		resourceFilter = new WebcenterWsrpEncoderFixResourceFilter();
		try {
			resourceFilter.init(null);
		} catch (PortletException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testWeblogicToken() {
		String fixed = resourceFilter.fixTokens("'zkver('7.0.1','2014022710','wsrp_rewrite?wsrp-urlType=resource&wsrp-secureURL=false&wsrp-url=http%3A%2F%2F127.0.0.1%3A7101%2Fzproducer%2F&wsrp-requiresRewrite=true&wsrp-sessionID=Yy75V6TMgmRz5GJL1Slw8kJ0hZ2dLXvCBrqTTtQG6h6tyXTpqTRM%211283917330%211438256652988&wsrp-resourceID=&wsrp-resourceState=&wsrp-resourceCacheability=&wsrp-preferOperation=false&wsrp-extensions=oracle%253Aescape-xml%3Dfalse%26oracle%253Astateless-resource%3Dtrue%26oracle%253AimmutableURI%3Dhttp%253A%252F%252F127.0.0.1%253A7101%252Fzproducer%26oracle%253AmutableURI%3D%252F%26oracle%253AmutableParameters%3D%26oracle%253AresourceStateForRewritewsrp_rewrite','wsrp_rewrite?wsrp-urlType=resource&wsrp-secureURL=false&wsrp-url=http%3A%2F%2F127.0.0.1%3A7101%2Fzproducer%2Fzkau&wsrp-requiresRewrite=true&wsrp-sessionID=Yy75V6TMgmRz5GJL1Slw8kJ0hZ2dLXvCBrqTTtQG6h6tyXTpqTRM%211283917330%211438256652988&wsrp-resourceID=&wsrp-resourceState=&wsrp-resourceCacheability=&wsrp-preferOperation=false&wsrp-extensions=oracle%253Aescape-xml%3Dfalse%26oracle%253Astateless-resource%3Dtrue%26oracle%253AimmutableURI%3Dhttp%253A%252F%252F127.0.0.1%253A7101%252Fzproducer%26oracle%253AmutableURI%3D%252Fzkau%26oracle%253AmutableParameters%3D%26oracle%253AresourceStateForRewrite/wsrp_rewrite',{},{ed:'e',dj:1,eu:{},eup:{}});}zk.load('zul.lang',function(){if(zk._p=zkpi('zul'))try{\n'");
		assertEquals("'zkver('7.0.1','2014022710','wsrp_rewrite?wsrp-urlType=resource&wsrp-secureURL=false&wsrp-url=http%3A%2F%2F127.0.0.1%3A7101%2Fzproducer%2F&wsrp-requiresRewrite=true&wsrp-sessionID=Yy75V6TMgmRz5GJL1Slw8kJ0hZ2dLXvCBrqTTtQG6h6tyXTpqTRM%211283917330%211438256652988&wsrp-resourceID=&wsrp-resourceState=&wsrp-resourceCacheability=&wsrp-preferOperation=false&wsrp-extensions=oracle%253Aescape-xml%3Dfalse%26oracle%253Astateless-resource%3Dtrue%26oracle%253AimmutableURI%3Dhttp%253A%252F%252F127.0.0.1%253A7101%252Fzproducer%26oracle%253AmutableURI%3D%252F%26oracle%253AmutableParameters%3D%26oracle%253AresourceStateForRewrite/wsrp_rewrite','wsrp_rewrite?wsrp-urlType=resource&wsrp-secureURL=false&wsrp-url=http%3A%2F%2F127.0.0.1%3A7101%2Fzproducer%2Fzkau&wsrp-requiresRewrite=true&wsrp-sessionID=Yy75V6TMgmRz5GJL1Slw8kJ0hZ2dLXvCBrqTTtQG6h6tyXTpqTRM%211283917330%211438256652988&wsrp-resourceID=&wsrp-resourceState=&wsrp-resourceCacheability=&wsrp-preferOperation=false&wsrp-extensions=oracle%253Aescape-xml%3Dfalse%26oracle%253Astateless-resource%3Dtrue%26oracle%253AimmutableURI%3Dhttp%253A%252F%252F127.0.0.1%253A7101%252Fzproducer%26oracle%253AmutableURI%3D%252Fzkau%26oracle%253AmutableParameters%3D%26oracle%253AresourceStateForRewrite/wsrp_rewrite',{},{ed:'e',dj:1,eu:{},eup:{}});}zk.load('zul.lang',function(){if(zk._p=zkpi('zul'))try{\n'", fixed);
	}

	@Test
	public void testWebsphereToken() {
		String fixed = resourceFilter.fixTokens("zkver('7.0.1','2014022710','wsrp_rewrite?wsrp-urlType=resource&wsrp-url=http%3a%2f%2feb-pur-migr-ufos.otr.ru%3a9083%2fzproducer%2f&wsrp-requiresRewrite=false&wsrp-secureURL=falsewsrp_rewrite','wsrp_rewrite?wsrp-urlType=resource&wsrp-url=http%3a%2f%2feb-pur-migr-ufos.otr.ru%3a9083%2fzproducer%2fzkau&wsrp-requiresRewrite=false&wsrp-secureURL=false/wsrp_rewrite',{},{ed:'e',dj:1,eu:{},eup:{}});}zk.load('zul.lang',function(){if(zk._p=zkpi('zul'))try{");
		assertEquals("zkver('7.0.1','2014022710','wsrp_rewrite?wsrp-urlType=resource&wsrp-url=http%3a%2f%2feb-pur-migr-ufos.otr.ru%3a9083%2fzproducer%2f&wsrp-requiresRewrite=false&wsrp-secureURL=false/wsrp_rewrite','wsrp_rewrite?wsrp-urlType=resource&wsrp-url=http%3a%2f%2feb-pur-migr-ufos.otr.ru%3a9083%2fzproducer%2fzkau&wsrp-requiresRewrite=false&wsrp-secureURL=false/wsrp_rewrite',{},{ed:'e',dj:1,eu:{},eup:{}});}zk.load('zul.lang',function(){if(zk._p=zkpi('zul'))try{", fixed);
	}
}
