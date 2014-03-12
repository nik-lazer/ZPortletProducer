package zk;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: comment
 * @author lazarev_nv 06.03.2014   12:31
 */
public class Test {
	public static void main(String[] args) {
		testURL();
	}

	private static void testURL() {
		URL wsdlUrl = null;
		try {
			wsdlUrl = new URL("http://eb-dev-sso.otr.ru:8888/sufdclient/portlets/wsrp2?WSDL");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		Pattern p = Pattern.compile("(\\/[\\w-]*\\/)");
		Matcher m = p.matcher(wsdlUrl.getPath());
		m.find();
		System.out.println(m.group(1));
//		while (m.find()) {
//			String path = m.group();
//			System.out.println(path);
//		}

		//try {
		//	wsdlUrl = new URL(wsdlUrl.getProtocol(), wsdlUrl.getHost(), wsdlUrl.getPort(), path);
		//} catch (MalformedURLException e) {
		//	e.printStackTrace();
		//}

	}

}
