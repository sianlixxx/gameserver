package com.ovgu.mmorpg;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.util.CharsetUtil;

import com.ovgu.mmorpg.utils.ConfigurationReader;
import com.ovgu.netty.handler.MessageServerHandler;
import com.ovgu.netty.network.GameServerPipelineFactory;

public class LaunchServer {
	ConfigurationReader cReader = null;

	/**
	 * @param args
	 */
	// channel group to manager all connected channel
	public static final ChannelGroup allChannels = new DefaultChannelGroup(
			"gameserver");

	public void run() {
		// TODO Auto-generated method stub
		readAllProperties();
		// start server

		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ServerBootstrap bootstrap = new ServerBootstrap(factory);

		// set a pipeline factory
		bootstrap.setPipelineFactory(new GameServerPipelineFactory());

		// open 8000 port
		Channel channel = bootstrap.bind(new InetSocketAddress(cReader
				.getGameServerPort()));

		allChannels.add(channel);
		waitForShutdownCommand();
		ChannelGroupFuture future = allChannels.close();
		future.awaitUninterruptibly();
		factory.releaseExternalResources();
	}

	private void waitForShutdownCommand() {
		try {
			Thread.sleep(10000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LaunchServer().run();
	}

	public void initialGameEnvironment() {

	}

	public void readAllProperties() {
		cReader = ConfigurationReader.getInstance();
		// System.out.println(cReader.getPersistTimePeriod());
	}
}
