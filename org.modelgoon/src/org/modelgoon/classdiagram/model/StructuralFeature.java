package org.modelgoon.classdiagram.model;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaElement;

public abstract class StructuralFeature<T extends IJavaElement> extends
		JavaModelElement<T> {

	private int flags;

	Visibility visibility = Visibility.DEFAULT;

	boolean isStatic = false;

	boolean isTransient = false;

	ClassModel declaringClass;

	public StructuralFeature() {
		super();
	}

	public void setDeclaringClass(final ClassModel declaringClass) {
		this.declaringClass = declaringClass;
	}

	public ClassModel getDeclaringClass() {
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

	@Override
	protected void updateJavaElement(final T javaElement) {
		// TODO Auto-generated method stub

	}

}
