package com.ovgu.mmorpg.cassandra.connection;

import java.util.logging.Logger;

import com.ovgu.mmorpg.utils.ConfigurationReader;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

//import org.apache.log4j.Logger;

public class CassandraHelper {
	private static CassandraHelper singletonHelper = null;
	//
	private final static Logger logger = Logger.getLogger(CassandraHelper.class
			.getName());

	private Cluster gameCluster;

	private String KeySpaceName;

	private StringSerializer stringSerializer = StringSerializer.get();

	/*
	 * only one instance , for the all game cluster
	 */
	public static CassandraHelper getCassandraHelperInstance() {
		if (singletonHelper == null) {
			System.out.println("===>creating new CassandraHelper===");
			singletonHelper = new CassandraHelper();
		}
		return singletonHelper;

	}

	private CassandraHelper() {
		ConfigurationReader reader = ConfigurationReader.getInstance();
		String clusterName = reader.getClusterName();
		String clusterIp = reader.getCassandraClusterIp();
		String host = reader.getSingleCassandraHost();
		KeySpaceName = reader.getKeySpaceName();// .getKeyspaceName();

		if (clusterIp == null || clusterIp.trim().length() == 0) {
			logger.info("===>creating single node cluster===");
			gameCluster = HFactory.getOrCreateCluster(clusterName, host);
		} else {
			gameCluster = createMultiNodesCuster(clusterName, clusterIp);
		}
		

	}

	private Cluster createMultiNodesCuster(String cname, String ips) {
		logger.info("===>creating multi node cluster===" + ips);
		CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator(
				ips);
		gameCluster = HFactory.getOrCreateCluster(cname,
				cassandraHostConfigurator);
		return gameCluster;

	}

	/*
	 * get the cassandra cluster
	 */
	public Cluster getGameCluster() {
		return gameCluster;
	}

	/*
	 * get keyspacename
	 */
	public String getKeySpaceName() {
		return KeySpaceName;
	}

	public StringSerializer getStringSerializer() {
		return stringSerializer;
	}

	public static void main(String[] args) {
		
	}

}
