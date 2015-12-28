package lan.test.lock.util;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.util.StringUtils;

/**
 * @author nik-lazer  23.12.2015   15:44
 */
public class HazelcastProvider {
	private static final String HAZELCAST_CONFIG_KEY = "hazelcast.config";
	private static final String HAZELCAST_LOGGING_KEY = "hazelcast.logging.type";
	private static final String HAZELCAST_HEALTH_MONITOR_KEY = "hazelcast.health.monitoring.level";
	private static HazelcastInstance instance;

	public static HazelcastInstance getInstance(String groupName, String groupPass) {
		init(groupName, groupPass);
		return instance;
	}

	private static void init(String groupName, String groupPass) {
		if (instance == null) {
			synchronized (HazelcastProvider.class) {
				if (instance == null) {
					instance = createInstance(groupName, groupPass);
				}
			}
		}
	}

	private static HazelcastInstance createInstance(String groupName, String groupPass) {
		String configLocation = System.getProperty(HAZELCAST_CONFIG_KEY);
		if (!StringUtils.isEmpty(configLocation)) {
			return Hazelcast.newHazelcastInstance();
		}

		Config config = new Config();
		config.setProperty(HAZELCAST_LOGGING_KEY, "slf4j");
		config.setProperty(HAZELCAST_HEALTH_MONITOR_KEY, "OFF");
		GroupConfig groupConfig = new GroupConfig();
		groupConfig.setName(groupName);
		groupConfig.setPassword(groupPass);
		config.setGroupConfig(groupConfig);
		return Hazelcast.newHazelcastInstance(config);
	}

	public static void shutdown() {
		if (instance != null) {
			instance.shutdown();
		}
	}

	public static IMap connect() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.addAddress("127.0.0.1:4000");
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		IMap map = client.getMap("lockMap");
		return map;
	}
}

