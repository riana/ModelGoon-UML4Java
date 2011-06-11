package org.modelgoon.sequencediagram.editparts;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.modelgoon.sequencediagram.model.CombinedStatementBlock;
import org.modelgoon.sequencediagram.model.StatementBlock;
import org.modelgoon.sequencediagram.ui.CombinedFragmentFigure;

public class CombinedStatementBlockEditPart extends AbstractGraphicalEditPart {

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		CombinedFragmentFigure lifelineFigure = (CombinedFragmentFigure) getFigure();
		CombinedStatementBlock object = (CombinedStatementBlock) getModel();
		lifelineFigure.setGuard(object.getExpression());
		refreshChildren();
	}

	@Override
	protected IFigure createFigure() {
		CombinedFragmentFigure lifelineFigure = new CombinedFragmentFigure();
		return lifelineFigure;
	}

	@Override
	protected List getModelChildren() {
		StatementBlock object = (StatementBlock) getModel();
		return object.getStatements();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DoNothingEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
	}

}
