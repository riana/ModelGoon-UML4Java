package org.modelgoon.sequencediagram.editparts;

import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.modelgoon.sequencediagram.model.MessageExchange;
import org.modelgoon.sequencediagram.ui.LifelineFigure;
import org.modelgoon.sequencediagram.ui.MessageConnection;

public class MessageExchangeEditPart extends AbstractGraphicalEditPart {

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		MessageConnection lifelineFigure = (MessageConnection) getFigure();
		MessageExchange object = (MessageExchange) getModel();

		Map registry = getViewer().getEditPartRegistry();
		CollaboratingObjectEditPart sourceEditPart = (CollaboratingObjectEditPart) registry
				.get(object.getSource());
		CollaboratingObjectEditPart destEditPart = (CollaboratingObjectEditPart) registry
				.get(object.getDestination());

		lifelineFigure.setMessage(object.getMessageName());
		lifelineFigure.setSource((LifelineFigure) sourceEditPart.getFigure());
		lifelineFigure
				.setDestination((LifelineFigure) destEditPart.getFigure());
		lifelineFigure.setDashed(object.isReturnMessage());
	}

	@Override
	protected IFigure createFigure() {
		MessageConnection lifelineFigure = new MessageConnection();
		return lifelineFigure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DoNothingEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
	}

}
