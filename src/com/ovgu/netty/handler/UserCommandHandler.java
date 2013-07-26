package com.ovgu.netty.handler;

import me.prettyprint.hector.api.mutation.MutationResult;

import org.jboss.netty.channel.Channel;

import com.ovgu.mmorpg.cassandra.dao.GamePlayerDao;
import com.ovgu.mmorpg.cassandra.factory.GamePlayerFactory;

public class UserCommandHandler {

	// static HashTable<Channel> v = new HashTable();

	public static void handleCommand(Channel ch, String string) {
		MutationResult mr;
		GamePlayerDao gpd = new GamePlayerDao();
		System.out.println("user send " + string);
		if (string.trim().toLowerCase().equals("a")) {
			System.out.println("Add a player "
					+ GamePlayerFactory.createPlayer());
			mr = gpd.addPlayer(GamePlayerFactory.createPlayer());
			String time = String.valueOf(mr.getExecutionTimeMicro());
			ch.write(time);
		}

		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}
}
