package lan.test;

import lan.test.portlet.zk.util.UIUtils;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link lan.test.portlet.zk.util.UIUtils}
 * @author nik-lazer  25.05.2015   12:06
 */
public class UIUtilsTest {
	@Test
	public void resloveDateTest() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, Calendar.MAY);
		c.set(Calendar.YEAR, 2015);
		c.set(Calendar.DAY_OF_MONTH, 25);
		c.set(Calendar.HOUR_OF_DAY, 13);
		c.set(Calendar.MINUTE, 26);
		c.set(Calendar.SECOND, 49);
		String name = UIUtils.resolveFileName(c);
		assertEquals("print-25-05-2015 1326-49.txt", name);
	}
}
