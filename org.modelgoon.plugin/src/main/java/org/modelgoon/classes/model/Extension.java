package org.modelgoon.classes.model;

import org.modelgoon.core.AbstractConnection;

public class Extension extends AbstractConnection {

	ClassElement child;

	ClassElement parent;

	public Extension() {
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

	public boolean isRealization() {
		return this.parent.isInterface();
	}

}
