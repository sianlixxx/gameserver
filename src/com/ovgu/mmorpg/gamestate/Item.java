package com.ovgu.mmorpg.gamestate;

import java.util.UUID;

public class Item {

	String uuidString;
	int price = 0;
	String itemName = "unnamed item";
	String description = "a item without name and description";
	String ownerUUID = "system_defaule";

	public String getOwnerUUID() {
		return ownerUUID;
	}

	public void setOwnerUUID(String ownerUUID) {
		this.ownerUUID = ownerUUID;
	}

	public Item() {
		uuidString = UUID.randomUUID().toString();
	}

	public String getStringUUID() {
		return uuidString;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String storeValue() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.itemName).append("/").append(this.description)
				.append("/").append(this.price);
		return sb.toString();
	}

}
