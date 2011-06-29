package org.modelgoon.classes.model;

public class Attribute extends StructuralFeature {

	boolean literal = false;

	public boolean isLiteral() {
		return this.literal;
	}

	public void setLiteral(final boolean literal) {
		this.literal = literal;
	}

}
