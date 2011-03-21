package org.modelgoon.classes.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelgoon.core.AbstractConnection;
import org.modelgoon.core.ModelElement;

public class ClassElement extends ModelElement {

	String qualifiedName;

	boolean isAbstract;

	boolean internal;

	boolean isInterface;

	Map<String, Attribute> attributes = new HashMap<String, Attribute>();

	List<AbstractConnection> incomingConnections = new ArrayList<AbstractConnection>();

	List<AbstractConnection> outgoingConnections = new ArrayList<AbstractConnection>();

	public final void setQualifiedName(final String qualifiedName) {
		this.qualifiedName = qualifiedName;
		propertyChanged();
	}

	public final String getQualifiedName() {
		return this.qualifiedName;
	}

	public void addAttribute(final Attribute attribute) {
		this.attributes.put(attribute.getName(), attribute);
		propertyChanged();
	}

	public Collection<Attribute> getAttributes() {
		return this.attributes.values();
	}

	public void setAbstract(final boolean isAbstract) {
		this.isAbstract = isAbstract;
		propertyChanged();
	}

	public boolean isAbstract() {
		return this.isAbstract;
	}

	public void setInterface(final boolean isInterface) {
		this.isInterface = isInterface;
		propertyChanged();
	}

	public boolean isInterface() {
		return this.isInterface;
	}

	public void setInternal(final boolean internal) {
		this.internal = internal;
		propertyChanged();
	}

	public boolean isInternal() {
		return this.internal;
	}

	public void addIncomingConnection(final AbstractConnection connection) {
		this.incomingConnections.add(connection);
		propertyChanged();
	}

	public void removeIncomingConnection(final AbstractConnection connection) {
		this.incomingConnections.add(connection);
		propertyChanged();
	}

	public List<AbstractConnection> getIncomingConnections() {
		return this.incomingConnections;
	}

	public List<AbstractConnection> getOutgoingConnections() {
		return this.outgoingConnections;
	}

	public void addOutgoingConnection(final AbstractConnection connection) {
		this.outgoingConnections.add(connection);
		propertyChanged();
	}

	public void removeOutgoingConnection(final AbstractConnection connection) {
		this.outgoingConnections.remove(connection);
	}
}
