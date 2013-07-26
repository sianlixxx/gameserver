package com.ovgu.mmorpg.unitest;

import java.io.Serializable;

public class Command implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2177896460997022320L;

	private String commandContext;

	public String getCommandContext() {
		return commandContext;
	}

	public void setCommandContext(String commandContext) {
		this.commandContext = commandContext;
	}

	public Command() {

	}
}
