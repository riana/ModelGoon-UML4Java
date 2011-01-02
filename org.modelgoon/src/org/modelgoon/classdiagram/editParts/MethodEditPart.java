package org.modelgoon.classdiagram.editParts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.modelgoon.classdiagram.figures.FieldFigure;
import org.modelgoon.classdiagram.model.StructuralFeature;

public class MethodEditPart extends JavaModelElementEditPart {

	@Override
	protected IFigure createFigure() {
		FieldFigure figure = new FieldFigure();
		return figure;
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		FieldFigure figure = (FieldFigure) getFigure();
		figure.setField((StructuralFeature) getModel());
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new FieldEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
	}

}
