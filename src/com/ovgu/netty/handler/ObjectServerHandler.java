package com.ovgu.netty.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.ovgu.mmorpg.unitest.Command;

/*
 * object transform
 */
public class ObjectServerHandler extends SimpleChannelHandler {

	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		//Command command = (Command) e.getMessage();
		System.out.print("command.getCommandContext()");
	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		System.err.println("Client has a error,Error cause:" + e.getCause());
		e.getChannel().close();
	}
}
