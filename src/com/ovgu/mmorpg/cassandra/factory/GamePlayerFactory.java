package com.ovgu.mmorpg.cassandra.factory;

import java.util.UUID;

import com.ovgu.mmorpg.gamestate.GamePlayer;
import com.ovgu.mmorpg.utils.Utils;

public class GamePlayerFactory {

	/**
	 * generate random player
	 * 
	 * @param args
	 */

	public static GamePlayer createPlayer() {
		GamePlayer player = new GamePlayer();
		player.setAttack(Utils.getRandomInt(100));
		player.setBirth(Utils.getRandomBirth());
		player.setClassId(Utils.getRandomInt(8));
		player.setCurrExp(0);
		player.setMaxExp(100000);
		player.setCurrHp(1000);
		player.setMaxHp(1000);
		player.setDefense(Utils.getRandomInt(100));
		player.setEmail(Utils.getRandomEmail());
		player.setHeroClass(Utils.getRandomHeroClass());
		//
		String[] race = Utils.getRandomRace().split(" ");
		player.setHeroRace(race[0]);
		player.setRaceId(Integer.valueOf(race[1]));
		player.setLastActiceDate(Utils.CurrentDateString);
		player.setLastActiveIp("127.0.0.1");
		player.setRegistDate(Utils.CurrentDateString);
		player.setStrength(Utils.getRandomInt(20));
		player.setTrueName(Utils.getRandomString(5));
		player.setMapId(1);

		player.setUUIDString(UUID.randomUUID().toString());
		player.setUserName(Utils.getRandomString(6));
		player.setUserPassword("player");

		return player;
	}

	public static GamePlayer createPlayer(String username, String password) {
		GamePlayer player = new GamePlayer();
		player.setAttack(Utils.getRandomInt(100));
		player.setBirth(Utils.getRandomBirth());
		player.setClassId(Utils.getRandomInt(8));
		player.setCurrExp(0);
		player.setMaxExp(100000);
		player.setCurrHp(1000);
		player.setMaxHp(1000);
		player.setDefense(Utils.getRandomInt(100));
		player.setEmail(Utils.getRandomEmail());
		player.setHeroClass(Utils.getRandomHeroClass());
		//
		String[] race = Utils.getRandomRace().split(" ");
		player.setHeroRace(race[0]);
		player.setRaceId(Integer.valueOf(race[1]));
		player.setLastActiceDate(Utils.CurrentDateString);
		player.setLastActiveIp("127.0.0.1");
		player.setRegistDate(Utils.CurrentDateString);
		player.setStrength(Utils.getRandomInt(20));
		player.setTrueName(Utils.getRandomString(5));
		player.setMapId(1);

		player.setUUIDString(UUID.randomUUID().toString());
		player.setUserName(username);
		player.setUserPassword(password);

		return player;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(GamePlayerFactory.createPlayer());
		}
	}

}
