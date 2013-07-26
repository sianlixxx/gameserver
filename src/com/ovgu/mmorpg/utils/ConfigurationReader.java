package com.ovgu.mmorpg.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigurationReader {

	private final static Logger logger = Logger
			.getLogger(ConfigurationReader.class.getName());

	private static ConfigurationReader creader;
	private int persistTimePeriod = 0;
	// the port which game server used
	private int gameServerPort = 8000;
	private String gameServerIp = "";

	private String clusterName = "";
	private String singleCassandraHost = "10.0.0.9:9160";
	private String keySpaceName = "JMMORPG";
	private String cassandraClusterIp = "";

	public int getPersistTimePeriod() {
		return persistTimePeriod;
	}

	public void setPersistTimePeriod(int persistTimePeriod) {
		this.persistTimePeriod = persistTimePeriod;
	}

	public int getGameServerPort() {
		return gameServerPort;
	}

	public void setGameServerPort(int gameServerPort) {
		this.gameServerPort = gameServerPort;
	}

	public String getGameServerIp() {
		return gameServerIp;
	}

	public void setGameServerIp(String gameServerIp) {
		this.gameServerIp = gameServerIp;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getSingleCassandraHost() {
		return singleCassandraHost;
	}

	public void setSingleCassandraHost(String singleCassandraHost) {
		this.singleCassandraHost = singleCassandraHost;
	}

	public String getKeySpaceName() {
		return keySpaceName;
	}

	public void setKeySpaceName(String keySpaceName) {
		this.keySpaceName = keySpaceName;
	}

	public String getCassandraClusterIp() {
		return cassandraClusterIp;
	}

	public void setCassandraClusterIp(String cassandraClusterIp) {
		this.cassandraClusterIp = cassandraClusterIp;
	}

	private ConfigurationReader() {
		logger.log(Level.INFO,
				"read property file from " + this.getPropertyFilePath());
		this.loadProperties();
	}

	/*
	 * return a singleton instance of properties reader
	 */
	public static ConfigurationReader getInstance() {
		if (creader == null) {
			creader = new ConfigurationReader();
		}
		System.out.println(creader.toSring());
		return creader;

	}

	private void loadProperties() {

		Properties p = new Properties();
		try {
			InputStream inputStream = new BufferedInputStream(
					new FileInputStream(getPropertyFilePath()
							+ "config.properties"));
			p.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//

		this.setGameServerIp(p.getProperty("GameServerIp"));
		this.setGameServerPort(Integer.parseInt(p.getProperty("GameServerPort")));
		this.setSingleCassandraHost(p.getProperty("SingleCassandraHost"));
		this.setClusterName(p.getProperty("CassandraClusterName"));
		this.setKeySpaceName(p.getProperty("KeySpaceName"));
		this.setCassandraClusterIp(p.getProperty("CassandraClusterIp"));

		this.setPersistTimePeriod(Integer.parseInt(p
				.getProperty("persistTimePeriod")));

	}

	public String toSring() {
		StringBuilder sBuilder = new StringBuilder(
				"Reading Configuration from file");
		sBuilder.append("\n").append("Game Server Ip       ==>  ")
				.append(this.getGameServerIp());
		sBuilder.append("\n").append("Game Server Port     ==>  ")
				.append(this.getGameServerPort());
		sBuilder.append("\n").append("Cassandra Single Host==>  ")
				.append(this.getSingleCassandraHost());
		sBuilder.append("\n").append("Cassandra Cluster Ip ==>  ")
				.append(this.getCassandraClusterIp());
		sBuilder.append("\n").append("KeySpace Name        ==>  ")
				.append(this.getKeySpaceName());
		sBuilder.append("\n").append("Cluster Name         ==>  ")
				.append(this.getClusterName());

		return sBuilder.toString();

	}

	private String getPropertyFilePath() {
		String path = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();

		return path.substring(0, path.lastIndexOf("/") - 3);
		// /home/parallels/workspace/gameserver/
	}

	/**
	 * read the properties
	 */
	public static void main(String[] args) {
		ConfigurationReader cr = new ConfigurationReader();
		logger.info(cr.toSring());
	}
}
