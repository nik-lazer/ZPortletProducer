package lan.test;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Example of URL enescaping by means Apache Commons Lang
 * @author nik-lazer  21.10.2015   16:32
 */
@RunWith(Parameterized.class)
public class UnEscapingTest {
	private String token;
	private String expected;

	public UnEscapingTest(String token, String expected) {
		this.token = token;
		this.expected = expected;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{"wsrp_rewrite?wsrp-urlType=resource&wsrp-windowState=wsrp%3Anormal&wsrp-mode=wsrp%3Aview&wsrp-resourceID=%2Fzproducer%2Fzkau%2Fview%2Fz_sjd%2Fdwnmed-1%2Fnnq1%2Fprint-21-10-2015%25201551-30.txt&wsrp-resourceCacheability=cacheLevelPage&wsrp-navigationalState=X0hlbGxvWktfV0FSX3pwcm9kdWNlcl93c3JwPTEm\\/wsrp_rewrite", "wsrp_rewrite?wsrp-urlType=resource&wsrp-windowState=wsrp%3Anormal&wsrp-mode=wsrp%3Aview&wsrp-resourceID=%2Fzproducer%2Fzkau%2Fview%2Fz_sjd%2Fdwnmed-1%2Fnnq1%2Fprint-21-10-2015%25201551-30.txt&wsrp-resourceCacheability=cacheLevelPage&wsrp-navigationalState=X0hlbGxvWktfV0FSX3pwcm9kdWNlcl93c3JwPTEm/wsrp_rewrite"},
				{"wsrp-urlType=resource&wsrp-windowState=wsrp%3Anormal&wsrp-mode=wsrp%3Aview&wsrp-resourceID=%2Fzproducer%2Fzkau%2Fview%2Fz_sjd%2Fdwnmed-1%2Fnnq1%2Fprint-21-10-2015%25201551-30.txt&wsrp-resourceCacheability=cacheLevelPage&wsrp-navigationalState=X0hlbGxvWktfV0FSX3pwcm9kdWNlcl93c3JwPTEm\\", "wsrp-urlType=resource&wsrp-windowState=wsrp%3Anormal&wsrp-mode=wsrp%3Aview&wsrp-resourceID=%2Fzproducer%2Fzkau%2Fview%2Fz_sjd%2Fdwnmed-1%2Fnnq1%2Fprint-21-10-2015%25201551-30.txt&wsrp-resourceCacheability=cacheLevelPage&wsrp-navigationalState=X0hlbGxvWktfV0FSX3pwcm9kdWNlcl93c3JwPTEm"}
		});
	}



	@Test
	public void testUnescape() {
		String unescapedToken = StringEscapeUtils.unescapeJava(token);
		assertEquals(expected, unescapedToken);
	}
}
