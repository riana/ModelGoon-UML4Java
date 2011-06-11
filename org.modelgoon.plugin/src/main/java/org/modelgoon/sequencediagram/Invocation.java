package org.modelgoon.sequencediagram;

public class Invocation {

	ParticipatingObject source;

	ParticipatingObject destination;

	Message message;

	public Invocation(final ParticipatingObject source,
			final ParticipatingObject destination, final Message message) {
		super();
		this.source = source;
		this.destination = destination;
		this.message = message;
	}

	public Message getMessage() {
		return this.message;
	}

	public ParticipatingObject getSource() {
		return this.source;
	}

	public ParticipatingObject getDestination() {
		return this.destination;
	}

}
