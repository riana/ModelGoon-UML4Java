package org.modelgoon.jdt.model;

import java.util.ArrayList;
import java.util.List;


public class CommunicationRelationship extends Relationship {

	List<String> messages = new ArrayList<String>();

	public void addMessage(final String message) {
		if (!this.messages.contains(message)) {
			this.messages.add(message);
		}
	}

	public void clearMessages() {
		this.messages.clear();
	}

	public List<String> getMessages() {
		return this.messages;
	}

}
