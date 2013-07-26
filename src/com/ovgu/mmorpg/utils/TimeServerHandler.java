package com.ovgu.mmorpg.utils;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class TimeServerHandler extends SimpleChannelHandler {
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Channel ch = e.getChannel();
		ch.write(e.getMessage());
		// ChannelBuffer buf = (ChannelBuffer) e.getMessage();
		// while (buf.readable()) {
		// System.out.println((char) buf.readByte());
		// System.out.flush();
		// }
	}

	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
		TimeServer.allChannels.add(e.getChannel());
	}

	/**
	 * channelConnected method will be invoked when a connection is established
	 */
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Channel ch = e.getChannel();

		UnixTime time = new UnixTime((int) (System.currentTimeMillis() / 1000));

		ChannelFuture f = ch.write(time);

		f.addListener(ChannelFutureListener.CLOSE);
		
		//ctx.sendDownstream(e);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		Channel ch = e.getChannel();
		ch.close();
	}
}
