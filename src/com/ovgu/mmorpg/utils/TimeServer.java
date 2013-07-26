package com.ovgu.mmorpg.utils;

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

public class TimeServer {
	static final ChannelGroup allChannels = new DefaultChannelGroup(
			"time-server");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ServerBootstrap bootstrap = new ServerBootstrap(factory);

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() {
				return Channels.pipeline(new TimeEncoder(),
						new TimeServerHandler());
			}

		});

		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		Channel channel = bootstrap.bind(new InetSocketAddress(8000));

		allChannels.add(channel);
		waitForShutdownCommand();
		ChannelGroupFuture future = allChannels.close();
		future.awaitUninterruptibly();
		factory.releaseExternalResources();

	}

	private static void waitForShutdownCommand() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
