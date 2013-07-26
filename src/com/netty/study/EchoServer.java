package com.netty.study;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class EchoServer {

	private final int port;
	private static final Logger logger = Logger.getLogger(EchoServer.class
			.getName());

	public EchoServer(int port) {
		this.port = port;
	}

	public void run() {
		// configure the server
		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ServerBootstrap bootstrap = new ServerBootstrap(factory);

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() {
				return Channels.pipeline(new EchoServerHandler());
			}

		});
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(8000));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new EchoServer(8000).run();
	}
}
