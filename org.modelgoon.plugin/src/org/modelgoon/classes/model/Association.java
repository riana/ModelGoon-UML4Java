package org.modelgoon.classes.model;

import org.modelgoon.core.AbstractConnection;

public class Association extends AbstractConnection {

	ClassElement child;

	ClassElement parent;

	String name;

	String multiplicity;

	boolean containment;

	public Association() {
	}

	public void setName(final String name) {
		this.name = name;
		propertyChanged();
	}

	public String getName() {
		return this.name;
	}

	public void setMultiplicity(final String multiplicity) {
		this.multiplicity = multiplicity;
		propertyChanged();
	}

	public String getMultiplicity() {
		return this.multiplicity;
	}

	public void setContainment(final boolean containment) {
		this.containment = containment;
	}

	public boolean isContainment() {
		return this.containment;
	}

	public void setChild(final ClassElement child) {
		this.child = child;
		child.addOutgoingConnection(this);
	}

	public void setParent(final ClassElement parent) {
		this.parent = parent;
		parent.addIncomingConnection(this);
	}

	public void disconnect() {
		this.child.removeOutgoingConnection(this);
		this.parent.removeIncomingConnection(this);
	}

}
