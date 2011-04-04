package org.modelgoon.classdiagram.editParts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.modelgoon.classes.figures.FeatureFigure;
import org.modelgoon.jdt.model.Field;

public class FieldEditPart extends JavaModelElementEditPart {

	@Override
	protected IFigure createFigure() {
		FeatureFigure figure = new FeatureFigure();
		return figure;
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		FeatureFigure figure = (FeatureFigure) getFigure();
		figure.setField((Field) getModel());
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new FieldEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
	}

}
