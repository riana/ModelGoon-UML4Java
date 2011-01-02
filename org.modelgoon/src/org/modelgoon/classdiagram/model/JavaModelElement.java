package org.modelgoon.classdiagram.model;

import org.eclipse.jdt.core.IJavaElement;

public abstract class JavaModelElement<T extends IJavaElement> extends
		ModelElement {

	private T javaElement;

	public final void setJavaElement(final T javaElement) {
		this.javaElement = javaElement;
		updateJavaElement(javaElement);
	}

	public final T getJavaElement() {
		return this.javaElement;
	}

	protected abstract void updateJavaElement(T javaElement);

}
