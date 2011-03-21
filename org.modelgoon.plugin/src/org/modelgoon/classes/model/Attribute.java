package org.modelgoon.classes.model;

import org.modelgoon.core.ModelElement;

public class Attribute extends ModelElement {

	String name;

	public void setName(final String name) {
		this.name = name;
		propertyChanged();
	}

	public String getName() {
		return this.name;
	}

}
