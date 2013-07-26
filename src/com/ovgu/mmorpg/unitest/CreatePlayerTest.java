package com.ovgu.mmorpg.unitest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreatePlayerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 20; i++) {
			exec.execute(new CreateUserMultiThread());
		}
		exec.shutdown();


	}

}
