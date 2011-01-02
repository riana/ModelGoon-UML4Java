package org.modelgoon.sequencediagram.model;

public class MessageExchange implements Statement {

	ColloboratingObject source;

	ColloboratingObject destination;

	String messageName;

	public ColloboratingObject getSource() {
		return this.source;
	}

	public void setSource(final ColloboratingObject source) {
		this.source = source;
	}

	public ColloboratingObject getDestination() {
		return this.destination;
	}

	public void setDestination(final ColloboratingObject destination) {
		this.destination = destination;
		destination.addIncomingMessage(this);
	}

	public String getMessageName() {
		return this.messageName;
	}

	public void setMessageName(final String messageName) {
		this.messageName = messageName;
	}

	// @Override
	// public String toString() {
	// return this.source.getName() + " / " + this.destination.getName() + "."
	// + this.messageName;
	// }

}
