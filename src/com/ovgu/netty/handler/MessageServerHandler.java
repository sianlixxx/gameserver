package com.ovgu.netty.handler;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.ovgu.mmorpg.LaunchServer;

public class MessageServerHandler extends SimpleChannelUpstreamHandler {
	private static final Logger logger = Logger
			.getLogger(MessageServerHandler.class.getName());

	public void handleUpStream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if (e instanceof ChannelStateEvent) {
			logger.info(e.toString());
		}
		super.handleUpstream(ctx, e);
	}

	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		// Cast to a String first
		// we know it is a String because we put some codec in Pipelinefactory
		String msg = (String) e.getMessage();

		String response = null;
		boolean close = false;
		if (msg.length() == 0) {
			response = "please type somethins.\r\n";
		} else if (msg.toLowerCase().equals("exit")) {
			response = "Have a good day!\r\n";
			close = true;
		} else {
			UserCommandHandler.handleCommand(e.getChannel(), msg);
		}

		// do not need to write a channelbuffer
		// the encoder inserted at pipeline will do the convertion
		// ChannelFuture future = e.getChannel().write(response);
		// System.out.println("can i run?");
		//
		// // close the connection after sending a msg
		// // if the client has sent 'bye'
		// if (close) {
		// future.addListener(ChannelFutureListener.CLOSE);
		// }

	}

	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
		LaunchServer.allChannels.add(e.getChannel());
	}

	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		System.out.println("Client connected");
		e.getChannel().write("it is " + new Date() + "now.\r\n");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.log(Level.WARNING, "Unexpected exception from downstream.",
				e.getCause());
		e.getChannel().close();
	}
}
