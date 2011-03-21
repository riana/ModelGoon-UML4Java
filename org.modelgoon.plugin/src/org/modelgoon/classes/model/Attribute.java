package org.modelgoon.classes.model;

import org.modelgoon.classdiagram.model.Visibility;
import org.modelgoon.core.ModelElement;

public class Attribute extends ModelElement {

	String name;

	String type;

	Visibility visibility = Visibility.DEFAULT;

	boolean literal = false;

	boolean isStatic = false;

	public void setName(final String name) {
		this.name = name;
		propertyChanged();
	}

	public String getName() {
		return this.name;
	}

	public void setType(final String type) {
		this.type = type;
		propertyChanged();
	}

	public String getType() {
		return this.type;
	}

	public Visibility getVisibility() {
		return this.visibility;
	}

	public void setVisibility(final Visibility visibility) {
		this.visibility = visibility;
		propertyChanged();
	}

	public boolean isLiteral() {
		return this.literal;
	}

	public void setLiteral(final boolean literal) {
		this.literal = literal;
	}

	public boolean isStatic() {
		return this.isStatic;
	}

	public void setStatic(final boolean isStatic) {
		this.isStatic = isStatic;
	}

}
