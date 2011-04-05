package org.modelgoon.sequencediagram.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.modelgoon.sequencediagram.model.StatementBlock;
import org.modelgoon.sequencediagram.ui.FrameFigure;

public class StatementBlockEditPart extends AbstractGraphicalEditPart {

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		FrameFigure lifelineFigure = (FrameFigure) getFigure();
		StatementBlock object = (StatementBlock) getModel();
		lifelineFigure.setLabel(object.getType().toString());
		lifelineFigure.setGuard(object.getExpression());
		refreshChildren();
	}

	@Override
	protected IFigure createFigure() {
		FrameFigure lifelineFigure = new FrameFigure();
		return lifelineFigure;
	}

	@Override
	protected List getModelChildren() {
		List children = new ArrayList();
		StatementBlock object = (StatementBlock) getModel();
		children.addAll(object.getStatements());
		children.addAll(object.getCombinedStatementBlocks());
		return children;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DoNothingEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
	}

}
