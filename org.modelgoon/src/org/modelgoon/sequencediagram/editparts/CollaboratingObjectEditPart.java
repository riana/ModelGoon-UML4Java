package org.modelgoon.sequencediagram.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.modelgoon.classdiagram.editParts.FieldEditPolicy;
import org.modelgoon.sequencediagram.model.ColloboratingObject;
import org.modelgoon.sequencediagram.ui.LifelineFigure;

public class CollaboratingObjectEditPart extends AbstractGraphicalEditPart {

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		LifelineFigure lifelineFigure = (LifelineFigure) getFigure();
		ColloboratingObject object = (ColloboratingObject) getModel();
		lifelineFigure.setLabel(object.getName() + ":" + object.getType());
		lifelineFigure.setActor(object.isActor());
	}

	@Override
	protected IFigure createFigure() {
		LifelineFigure lifelineFigure = new LifelineFigure();
		return lifelineFigure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new FieldEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
	}

}
