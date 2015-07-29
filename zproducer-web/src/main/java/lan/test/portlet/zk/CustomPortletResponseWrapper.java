package lan.test.portlet.zk;

import javax.portlet.ResourceResponse;
import javax.portlet.filter.ResourceResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @see <a href="https://github.com/v0v87/zkoss-wsrp">zkoss-wsrp</a>
 *
 * @author solovyev.vladimir
 * @since 25.05.2015
 */
public class CustomPortletResponseWrapper extends ResourceResponseWrapper {
	private ByteArrayPrintWriter output;
	private boolean usingWriter;

	public CustomPortletResponseWrapper(ResourceResponse response) {
		super(response);
		usingWriter = false;
		output = new ByteArrayPrintWriter();
	}

	public byte[] getByteArray() {
		return output.toByteArray();
	}

	@Override
	public OutputStream getPortletOutputStream() throws IOException {
		// will error out, if in use
		if (usingWriter) {
			super.getPortletOutputStream();
		}
		usingWriter = true;
		return output.getStream();
	}


	@Override
	public PrintWriter getWriter() throws IOException {
		// will error out, if in use
		if (usingWriter) {
			super.getWriter();
		}
		usingWriter = true;
		return output.getWriter();
	}

	public String toString() {
		return output.toString();
	}

	public byte[] getByteArrayWithFlash() {
		return output.toByteArrayWithFlash();
	}

	private static class ByteArrayPortletStream extends OutputStream {
		ByteArrayOutputStream baos;

		ByteArrayPortletStream(ByteArrayOutputStream baos) {
			this.baos = baos;
		}

		@Override
		public void write(int param) throws IOException {
			baos.write(param);
		}
	}

	private static class ByteArrayPrintWriter {

		private ByteArrayOutputStream baos = new ByteArrayOutputStream();

		private PrintWriter pw = new PrintWriter(baos);

		private OutputStream sos = new ByteArrayPortletStream(baos);

		public PrintWriter getWriter() {
			return pw;
		}

		public OutputStream getStream() {
			return sos;
		}

		byte[] toByteArray() {
			return baos.toByteArray();
		}

		public byte[] toByteArrayWithFlash() {
			byte[] ret = baos.toByteArray();
			try {
				baos.flush();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ret;
		}
	}
}