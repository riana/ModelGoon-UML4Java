package org.modelgoon.classes.model;

import org.modelgoon.core.ModelElement;

public class StructuralFeature extends ModelElement {

	protected String name;
	protected String type;
	protected Visibility visibility = Visibility.DEFAULT;
	protected boolean isStatic = false;

	public StructuralFeature() {
		super();
	}

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

	public boolean isStatic() {
		return this.isStatic;
	}

	public void setStatic(final boolean isStatic) {
		this.isStatic = isStatic;
	}

}