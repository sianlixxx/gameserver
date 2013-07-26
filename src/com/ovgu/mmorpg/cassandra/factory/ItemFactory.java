package com.ovgu.mmorpg.cassandra.factory;

import com.ovgu.mmorpg.gamestate.Item;
import com.ovgu.mmorpg.utils.Utils;

public class ItemFactory {

	public static Item createItem() {
		int price = Utils.getRandomInt(1000);
		String name = "";
		String desc = "";
		Item item = new Item();
		if (price > 300 && price <= 600) {
			name = "goot item";
			desc = "this is a good item with good price";
		} else if (price > 600 && price <= 900) {
			name = "super item";
			desc = "this is a super item with expensive price";
		} else if (price > 900) {
			name = "fantastic item";
			desc = "this is a fantastic item";
		} else {
			name = "normal item";
			desc = "a normal item with little price";
		}
		item.setDescription(desc);
		item.setItemName(name);
		item.setPrice(price);
		return item;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
