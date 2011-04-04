package org.modelgoon.jdt.model;

import org.modelgoon.core.AbstractConnection;

public class Relationship extends AbstractConnection {

	public static final String SOURCE = "source";

	public static final String DESTINATION = "destination";

	private UMLClass source;

	private UMLClass destination;

	public UMLClass getSource() {
		return this.source;
	}

	public void setSource(final UMLClass source) {
		this.source = source;
		propertyChanged();
	}

	public UMLClass getDestination() {
		return this.destination;
	}

	public void setDestination(final UMLClass destination) {
		this.destination = destination;
		propertyChanged();
	}

}
