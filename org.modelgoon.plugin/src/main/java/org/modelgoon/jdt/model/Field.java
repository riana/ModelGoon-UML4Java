package org.modelgoon.jdt.model;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.dom.FieldDeclaration;

public class Field extends StructuralFeature {

	Type type = new Type();

	String attributeString;

	boolean isAssociation = false;

	boolean literal = false;

	boolean singleton = false;

	IField jdtField;

	public Field() {
		super();
	}

	public boolean isSingleton() {
		return this.singleton;
	}

	public boolean isAssociation() {
		return this.isAssociation;
	}

	public boolean isLiteral() {
		return this.literal;
	}

	public Type getType() {
		return this.type;
	}

	@Override
	public String toString() {
		if (isLiteral()) {
			this.attributeString = getName();
		}
		return this.attributeString;
	}

	public void update(final FieldDeclaration node) {

		setFlags(node.getModifiers());

		this.type.update(node.getType());

		this.singleton = this.isStatic
				&& (getDeclaringClass().getQualifiedName().equals(this.type
						.getQualifiedName()));

		this.attributeString = getVisibilityString() + getName() + ":"
				+ this.type.getName();
	}

	public void setAssociation(final boolean b) {
		this.isAssociation = b;
	}

	public void setLiteral(final boolean b) {
		this.literal = b;
	}

	public IField getJdtField() {
		return this.jdtField;
	}

	public void setJdtField(final IField jdtField) {
		this.jdtField = jdtField;
	}

}
