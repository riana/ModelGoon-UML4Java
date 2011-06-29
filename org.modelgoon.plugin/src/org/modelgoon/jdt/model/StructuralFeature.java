package org.modelgoon.jdt.model;

import org.eclipse.jdt.core.Flags;
import org.modelgoon.core.NamedModelElement;

public abstract class StructuralFeature extends NamedModelElement {

	private int flags;

	Visibility visibility = Visibility.DEFAULT;

	boolean isStatic = false;

	boolean isTransient = false;

	UMLClass declaringClass;

	public StructuralFeature() {
		super();
	}

	public void setDeclaringClass(final UMLClass declaringClass) {
		this.declaringClass = declaringClass;
	}

	public UMLClass getDeclaringClass() {
		return this.declaringClass;
	}

	public void setFlags(final int flags) {
		this.flags = flags;
		if (Flags.isPackageDefault(flags)) {
			this.visibility = Visibility.DEFAULT;
		} else if (Flags.isPrivate(flags)) {
			this.visibility = Visibility.PRIVATE;
		} else if (Flags.isProtected(flags)) {
			this.visibility = Visibility.PROTECTED;
		} else if (Flags.isPublic(flags)) {
			this.visibility = Visibility.PUBLIC;
		}
		this.isStatic = Flags.isStatic(this.flags);
		this.isTransient = Flags.isTransient(flags);
	}

	public Visibility getVisibility() {
		return this.visibility;
	}

	public String getVisibilityString() {
		return this.visibility.getVisibilityString();
	}

	public boolean isStatic() {
		return this.isStatic;
	}

	public boolean isTransient() {
		return this.isTransient;
	}

}
