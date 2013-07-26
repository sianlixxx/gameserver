package com.ovgu.mmorpg.utils;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class DiscardServerHandler extends SimpleChannelHandler {

	private static final Logger logger = Logger
			.getLogger(DiscardServerHandler.class.getName());

	private long transferredBytes;

	private long getTransferredBytes() {
		return transferredBytes;
	}

	public void handleUpStream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if (e instanceof ChannelStateEvent) {
			logger.info(e.toString());
		}
		super.handleUpstream(ctx, e);
	}

	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		 Channel ch = e.getChannel();
		 ch.write(e.getMessage());
//		ChannelBuffer buf = (ChannelBuffer) e.getMessage();
//		while (buf.readable()) {
//			System.out.println((char) buf.readByte());
//			System.out.flush();
//		}
	}

	/**
	 * channelConnected method will be invoked when a connection is established
	 */
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		// Channel ch = e.getChannel();
		// // allocate a new buffer which will contain the message,we are going
		// to
		// // write a 32-bit integer,and
		// // therefore we need a ChannelBuffer whose capacity is 4 bytes
		// ChannelBuffer time = ChannelBuffers.buffer(4);
		// time.writeInt((int) (System.currentTimeMillis() / 1000L +
		// 2208988899L));
		//
		// // the write method returns a ChannelFuture,it represents an I/O
		// // operation which has not yet occurred
		// // it meas,any requested operation might not have been performed yet.
		// // because all operations are asynchronous in
		// // netty
		// ChannelFuture f = ch.write(time);
		//
		// f.addListener(new ChannelFutureListener() {
		// public void operationComplete(ChannelFuture future) {
		// Channel ch = future.getChannel();
		// // close might also not close the connection immediately, and it
		// // returns a channelfuture
		// ch.close();
		// }
		// });
	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		Channel ch = e.getChannel();
		ch.close();
	}
}
