package lan.test.lock.service;

import com.hazelcast.core.IMap;

/**
 * @author nik-lazer  23.12.2015   15:42
 */
public class LockService {
	private final String KEY = "key";
	protected IMap locks;


	public boolean isLocked() {
		return locks.isLocked(KEY);
	}

	public void lock() {
		locks.lock(KEY);
	}

	public void unlock() {
		locks.unlock(KEY);
	}

	public void setLocks(IMap locks) {
		this.locks = locks;
	}
}
