package org.modelgoon.sequencediagram.model;

import java.util.ArrayList;
import java.util.List;

public class ColloboratingObject {

	String name = "";

	String type = "";

	boolean actor;

	List<MessageExchange> incomingMessages = new ArrayList<MessageExchange>();

	public void setActor(final boolean actor) {
		this.actor = actor;
	}

	public boolean isActor() {
		return this.actor;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void addIncomingMessage(final MessageExchange incomingMessage) {
		this.incomingMessages.add(incomingMessage);
	}

	public boolean hasIncomingMessages() {
		return !this.incomingMessages.isEmpty();
	}

}
