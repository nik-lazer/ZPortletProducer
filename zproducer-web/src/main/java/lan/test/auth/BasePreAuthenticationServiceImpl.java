package lan.test.auth;

/**
 * Base pre auth implementation. Divides getting user's name and setting auth
 * @author nik-lazer  28.10.2015   11:47
 */
public abstract class BasePreAuthenticationServiceImpl<T> implements PreAuthenticationService<T> {
	@Override
	public void preAuth(T servletRequest) {
		doAuth(servletRequest, getUserName(servletRequest));
	}

	protected abstract String getUserName(T servletRequest);

	protected abstract void doAuth(T servletRequest, String userName);
}
