package lan.test;

import lan.test.config.ApplicationContextProvider;
import lan.test.config.ConfigBean;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Test for spring beans initialization
 * @author nik-lazer  19.05.2015   12:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class BeansTest {
	@Test
	@Ignore
	public void configTest() {
		ConfigBean configBean = ApplicationContextProvider.getConfig();
		assertNotNull(configBean);
	}
}
