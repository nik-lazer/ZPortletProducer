package lan.test.portlet.zk.wsrp;

import org.junit.Before;
import org.junit.Test;

import javax.portlet.PortletException;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link lan.test.portlet.zk.wsrp.WSRPUtils}
 * @author nik-lazer  30.07.2015   14:49
 */
public class WSRPUtilsTest {
	@Test
	public void testWeblogicToken() {
		String fixed = WSRPUtils.fixTokens("'zkver('7.0.1','2014022710','wsrp_rewrite?wsrp-urlType=resource&wsrp-secureURL=false&wsrp-url=http%3A%2F%2F127.0.0.1%3A7101%2Fzproducer%2F&wsrp-requiresRewrite=true&wsrp-sessionID=Yy75V6TMgmRz5GJL1Slw8kJ0hZ2dLXvCBrqTTtQG6h6tyXTpqTRM%211283917330%211438256652988&wsrp-resourceID=&wsrp-resourceState=&wsrp-resourceCacheability=&wsrp-preferOperation=false&wsrp-extensions=oracle%253Aescape-xml%3Dfalse%26oracle%253Astateless-resource%3Dtrue%26oracle%253AimmutableURI%3Dhttp%253A%252F%252F127.0.0.1%253A7101%252Fzproducer%26oracle%253AmutableURI%3D%252F%26oracle%253AmutableParameters%3D%26oracle%253AresourceStateForRewritewsrp_rewrite','wsrp_rewrite?wsrp-urlType=resource&wsrp-secureURL=false&wsrp-url=http%3A%2F%2F127.0.0.1%3A7101%2Fzproducer%2Fzkau&wsrp-requiresRewrite=true&wsrp-sessionID=Yy75V6TMgmRz5GJL1Slw8kJ0hZ2dLXvCBrqTTtQG6h6tyXTpqTRM%211283917330%211438256652988&wsrp-resourceID=&wsrp-resourceState=&wsrp-resourceCacheability=&wsrp-preferOperation=false&wsrp-extensions=oracle%253Aescape-xml%3Dfalse%26oracle%253Astateless-resource%3Dtrue%26oracle%253AimmutableURI%3Dhttp%253A%252F%252F127.0.0.1%253A7101%252Fzproducer%26oracle%253AmutableURI%3D%252Fzkau%26oracle%253AmutableParameters%3D%26oracle%253AresourceStateForRewrite/wsrp_rewrite',{},{ed:'e',dj:1,eu:{},eup:{}});}zk.load('zul.lang',function(){if(zk._p=zkpi('zul'))try{\n'");
		assertEquals("'zkver('7.0.1','2014022710','wsrp_rewrite?wsrp-urlType=resource&wsrp-secureURL=false&wsrp-url=http%3A%2F%2F127.0.0.1%3A7101%2Fzproducer%2F&wsrp-requiresRewrite=true&wsrp-sessionID=Yy75V6TMgmRz5GJL1Slw8kJ0hZ2dLXvCBrqTTtQG6h6tyXTpqTRM%211283917330%211438256652988&wsrp-resourceID=&wsrp-resourceState=&wsrp-resourceCacheability=&wsrp-preferOperation=false&wsrp-extensions=oracle%253Aescape-xml%3Dfalse%26oracle%253Astateless-resource%3Dtrue%26oracle%253AimmutableURI%3Dhttp%253A%252F%252F127.0.0.1%253A7101%252Fzproducer%26oracle%253AmutableURI%3D%252F%26oracle%253AmutableParameters%3D%26oracle%253AresourceStateForRewrite/wsrp_rewrite','wsrp_rewrite?wsrp-urlType=resource&wsrp-secureURL=false&wsrp-url=http%3A%2F%2F127.0.0.1%3A7101%2Fzproducer%2Fzkau&wsrp-requiresRewrite=true&wsrp-sessionID=Yy75V6TMgmRz5GJL1Slw8kJ0hZ2dLXvCBrqTTtQG6h6tyXTpqTRM%211283917330%211438256652988&wsrp-resourceID=&wsrp-resourceState=&wsrp-resourceCacheability=&wsrp-preferOperation=false&wsrp-extensions=oracle%253Aescape-xml%3Dfalse%26oracle%253Astateless-resource%3Dtrue%26oracle%253AimmutableURI%3Dhttp%253A%252F%252F127.0.0.1%253A7101%252Fzproducer%26oracle%253AmutableURI%3D%252Fzkau%26oracle%253AmutableParameters%3D%26oracle%253AresourceStateForRewrite/wsrp_rewrite',{},{ed:'e',dj:1,eu:{},eup:{}});}zk.load('zul.lang',function(){if(zk._p=zkpi('zul'))try{\n'", fixed);
	}

	@Test
	public void testWebsphereToken() {
		String fixed = WSRPUtils.fixTokens("zkver('7.0.1','2014022710','wsrp_rewrite?wsrp-urlType=resource&wsrp-url=http%3a%2f%2feb-pur-migr-ufos.otr.ru%3a9083%2fzproducer%2f&wsrp-requiresRewrite=false&wsrp-secureURL=falsewsrp_rewrite','wsrp_rewrite?wsrp-urlType=resource&wsrp-url=http%3a%2f%2feb-pur-migr-ufos.otr.ru%3a9083%2fzproducer%2fzkau&wsrp-requiresRewrite=false&wsrp-secureURL=false/wsrp_rewrite',{},{ed:'e',dj:1,eu:{},eup:{}});}zk.load('zul.lang',function(){if(zk._p=zkpi('zul'))try{");
		assertEquals("zkver('7.0.1','2014022710','wsrp_rewrite?wsrp-urlType=resource&wsrp-url=http%3a%2f%2feb-pur-migr-ufos.otr.ru%3a9083%2fzproducer%2f&wsrp-requiresRewrite=false&wsrp-secureURL=false/wsrp_rewrite','wsrp_rewrite?wsrp-urlType=resource&wsrp-url=http%3a%2f%2feb-pur-migr-ufos.otr.ru%3a9083%2fzproducer%2fzkau&wsrp-requiresRewrite=false&wsrp-secureURL=false/wsrp_rewrite',{},{ed:'e',dj:1,eu:{},eup:{}});}zk.load('zul.lang',function(){if(zk._p=zkpi('zul'))try{", fixed);
	}

	@Test
	public void removeTokens() {
		String withToken = "gzip,targzip, targzip7z ,deflate, gzip-compressed,compressed";
		String withoutToken = "deflate,compressed";
		String anotherDataToken = "qwert:tarqwert: targqwerty7z :deflate: qwert-compressed:compressed";
		assertEquals("deflate,compressed", WSRPUtils.removeTokens(withToken, "gzip", ","));
		assertEquals("deflate,compressed", WSRPUtils.removeTokens(withoutToken, "gzip", ","));
		assertEquals("deflate,compressed", WSRPUtils.removeTokens(withoutToken, "qwert", ":"));
	}

	@Test
	public void overwriteWsrpUrlTest() {
		String resourceUrl = "wsrp_rewrite?wsrp-urlType=resource&wsrp-windowState=wsrp%3Anormal&wsrp-mode=wsrp%3Aview&wsrp-resourceCacheability=cacheLevelPage&wsrp-navigationalState=X0hlbGxvWktfV0FSX3pwcm9kdWNlcl93c3JwPTEm/wsrp_rewrite";
		String httpUrl = "http://tomee.lan:58080/c/portal/layout";
		String urlToEncode = "/zproducer/zksandbox.js.dsp";
		String expectedUrl = "wsrp_rewrite?wsrp-urlType=resource&wsrp-url=http%3a%2f%2ftomee.lan%3a58080%2fzproducer%2fzksandbox.js.dsp&wsrp-windowState=wsrp%3Anormal&wsrp-mode=wsrp%3Aview&wsrp-resourceCacheability=cacheLevelPage&wsrp-navigationalState=X0hlbGxvWktfV0FSX3pwcm9kdWNlcl93c3JwPTEm/wsrp_rewrite";
		assertEquals(expectedUrl, WSRPUtils.overwriteWsrpUrl(resourceUrl, httpUrl, urlToEncode));
	}

}
