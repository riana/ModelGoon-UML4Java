package org.modelgoon.jdt.model;

import org.modelgoon.core.AbstractConnection;

public class Relationship extends AbstractConnection {

	public static final String SOURCE = "source";

	public static final String DESTINATION = "destination";

	private UMLClass source;

	private UMLClass destination;

	public String destinationClassName;

	public UMLClass getSource() {
		return this.source;
	}

	public void setSource(final UMLClass source) {
		this.source = source;
		propertyChanged();
	}

	public String getDestinationClassName() {
		if (this.destination != null) {
			return this.destination.getQualifiedName();
		}
		return this.destinationClassName;
	}

	public void setDestinationClassName(final String destinationClassName) {
		this.destinationClassName = destinationClassName;
	}

	public UMLClass getDestination() {
		if (this.destination == null) {
			this.destination = this.source
					.resolveUMLClass(this.destinationClassName);
			if (this.destination != null) {
				this.destination.addIncomingRelationship(this);
			} else {
				System.out.println("Unresolved Class : "
						+ this.destinationClassName);
			}
		}
		return this.destination;
	}

	public void setDestination(final UMLClass destination) {
		this.destination = destination;
		propertyChanged();
	}

}
