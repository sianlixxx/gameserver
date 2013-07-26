package com.ovgu.mmorpg.gamestate;

import java.io.Serializable;

public class GamePlayer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -822564783710273416L;

	/**
	 * @param args
	 */
	// row key
	String uuidString;
	// for login , name and password
	String userName;
	// actually ,we don't need a password in cassandra
	String userPassword;

	String trueName;
	String birth;
	String email;
	String lastActiveIp;
	String lastActiceDate;
	String registDate;

	String heroClass;

	int raceId;
	String heroRace;
	int currHp;
	int MaxHp;

	int currExp;
	int MaxExp;

	int strength;
	int attack;
	int defense;
	int mapId;
	int classId;
	Inventory inventory;
	
	int locationX;
	int locationY;

	public GamePlayer() {

	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getRaceId() {
		return raceId;
	}

	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}

	public String getUUIDString() {
		return uuidString;
	}

	public void setUUIDString(String uuidString) {
		this.uuidString = uuidString;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;

	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastActiveIp() {
		return lastActiveIp;
	}

	public void setLastActiveIp(String lastActiveIp) {
		this.lastActiveIp = lastActiveIp;
	}

	public String getLastActiceDate() {
		return lastActiceDate;
	}

	public void setLastActiceDate(String lastActiceDate) {
		this.lastActiceDate = lastActiceDate;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getHeroClass() {
		return heroClass;
	}

	public void setHeroClass(String heroClass) {
		this.heroClass = heroClass;
	}

	public String getHeroRace() {
		return heroRace;
	}

	public void setHeroRace(String heroRace) {
		this.heroRace = heroRace;
	}

	public int getCurrHp() {
		return currHp;
	}

	public void setCurrHp(int currHp) {
		this.currHp = currHp;
	}

	public int getMaxHp() {
		return MaxHp;
	}

	public void setMaxHp(int maxHp) {
		MaxHp = maxHp;
	}

	public int getCurrExp() {
		return currExp;
	}

	public void setCurrExp(int currExp) {
		this.currExp = currExp;
	}

	public int getMaxExp() {
		return MaxExp;
	}

	public void setMaxExp(int maxExp) {
		MaxExp = maxExp;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String toString() {
		StringBuilder sbUser = new StringBuilder("GamePlayer Information");
		sbUser.append("RowKey :");
		sbUser.append(uuidString);
		sbUser.append("  userName =");
		sbUser.append(userName);
		sbUser.append("  userPassword =");
		sbUser.append(userPassword);
		sbUser.append("  trueName =");
		sbUser.append(trueName);
		sbUser.append("  birth=");
		sbUser.append(birth);
		sbUser.append("  classid=");
		sbUser.append(classId);
		sbUser.append("  heroClass =");
		sbUser.append(heroClass);
		sbUser.append("  raceid =");
		sbUser.append(raceId);
		sbUser.append("  race =");
		sbUser.append(heroRace);

		return sbUser.toString();
	}
}
