package com.ovgu.mmorpg.gamestate;

import java.util.Vector;

public class Inventory {

	int count;
	Vector<Item> vo;
	int money = 0;

	public Inventory() {
		count = 0;
		vo = new Vector<Item>();
	}

	public void addItem(Item item) {
		vo.add(item);
	}

	public void delItem(int index) {
		vo.remove(index);
	}

	public int getSize() {
		return vo.size();
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int mon) {
		this.money = mon;
	}

}
