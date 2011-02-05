package org.modelgoon.classdiagram.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ModelElement {

	public final static String NAME = "name";

	private String name;

	final private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public final String getName() {
		return this.name;
	}

	public final void setName(final String name) {
		this.name = name;
		firePropertyChange(ModelElement.NAME);
	}

	protected final void firePropertyChange(final String propertyName) {
		this.propertyChangeSupport
				.firePropertyChange(propertyName, true, false);
	}

	public final void addPropertyChangeListener(
			final PropertyChangeListener arg0) {
		this.propertyChangeSupport.addPropertyChangeListener(arg0);
	}

	public final void removePropertyChangeListener(
			final PropertyChangeListener arg0) {
		this.propertyChangeSupport.removePropertyChangeListener(arg0);
	}

}
