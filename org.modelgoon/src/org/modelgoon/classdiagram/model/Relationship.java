package org.modelgoon.classdiagram.model;


public class Relationship extends ModelElement {

	public static final String SOURCE = "source";

	public static final String DESTINATION = "destination";

	private ClassModel source;

	private ClassModel destination;

	public ClassModel getSource() {
		return this.source;
	}

	public void setSource(final ClassModel source) {
		this.source = source;
		firePropertyChange(Relationship.SOURCE);
	}

	public ClassModel getDestination() {
		return this.destination;
	}

	public void setDestination(final ClassModel destination) {
		this.destination = destination;
		firePropertyChange(Relationship.DESTINATION);
	}

}
