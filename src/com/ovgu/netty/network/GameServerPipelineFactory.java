package com.ovgu.netty.network;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import com.ovgu.netty.handler.MessageServerHandler;

import static org.jboss.netty.channel.Channels.*;

public class GameServerPipelineFactory implements ChannelPipelineFactory {

	static OrderedMemoryAwareThreadPoolExecutor e = new OrderedMemoryAwareThreadPoolExecutor(
			100, 0, 0);
	static ExecutionHandler executionHandler = new ExecutionHandler(e);

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		// create a default pipeline implementation.

		ChannelPipeline pipeline = pipeline();
		// add the text line codec combination first
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
				Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
		// and then business logic time consumed, but not cpu bound. db access
		// pipeline.addLast("executor", executionHandler);
		pipeline.addLast("handler", new MessageServerHandler());
		return pipeline;
	}
}
