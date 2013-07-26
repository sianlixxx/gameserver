package com.ovgu.mmorpg.unitest;

import com.ovgu.mmorpg.cassandra.dao.GamePlayerDao;
import com.ovgu.mmorpg.cassandra.factory.GamePlayerFactory;

public class CreateUserMultiThread implements Runnable {

	@Override
	public void run() {
		long starttime = System.currentTimeMillis();
		int i = 0;
		GamePlayerDao gp = new GamePlayerDao();
		long count = 0;
		while (i < 1000) {
			i++;
			count = count
					+ gp.addPlayer(GamePlayerFactory.createPlayer())
							.getExecutionTimeMicro();
		}
		System.out.println("thread over " + Thread.currentThread().getName()
				+ " " + (System.currentTimeMillis() - starttime) + " micro"
				+ count);
	}

}
