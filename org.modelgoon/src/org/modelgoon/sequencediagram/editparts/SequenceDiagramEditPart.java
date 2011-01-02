package org.modelgoon.sequencediagram.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.modelgoon.sequencediagram.model.InteractionModel;
import org.modelgoon.sequencediagram.ui.SequenceDiagramFigure;

public class SequenceDiagramEditPart extends AbstractGraphicalEditPart {

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new XYLayoutEditPolicy() {

			@Override
			protected Command createChangeConstraintCommand(
					final EditPart child, final Object constraint) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected Command getCreateCommand(final CreateRequest request) {
				// TODO Auto-generated method stub
				return null;
			}

		});
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshChildren();
	}

	@Override
	protected List getModelChildren() {
		List children = new ArrayList();
		InteractionModel interactionModel = (InteractionModel) getModel();
		children.addAll(interactionModel.getObjects());
		children.addAll(interactionModel.getStatements());
		return children;
	}

	@Override
	protected IFigure createFigure() {
		SequenceDiagramFigure figure = new SequenceDiagramFigure();
		figure.setSize(800, 800);
		return figure;
	}
}
