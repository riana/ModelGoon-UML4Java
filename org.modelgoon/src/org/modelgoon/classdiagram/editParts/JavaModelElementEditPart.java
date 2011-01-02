package org.modelgoon.classdiagram.editParts;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.modelgoon.classdiagram.model.JavaModelElement;

public abstract class JavaModelElementEditPart extends
		AbstractGraphicalEditPart {

	JavaModelElement<?> javaModelElement;

	@Override
	public void setModel(final Object model) {
		super.setModel(model);
		if (model instanceof JavaModelElement<?>) {
			this.javaModelElement = (JavaModelElement<?>) model;
		}
	}

	public JavaModelElement<?> getJavaModelElement() {
		return this.javaModelElement;
	}

}
