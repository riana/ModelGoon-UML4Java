package org.modelgoon.jdt.editparts;

import org.eclipse.draw2d.IFigure;
import org.modelgoon.classes.figures.AssociationConnection;
import org.modelgoon.core.ui.AbstractLinkEditPart;
import org.modelgoon.jdt.model.AssociationRelationShip;

public class AssociationEditPart extends
		AbstractLinkEditPart<AssociationRelationShip> {

	AssociationConnection polylineConnection;

	@Override
	protected IFigure createFigure() {

		this.polylineConnection = new AssociationConnection();

		return this.polylineConnection;
	}

	@Override
	protected void doRefreshVisuals(final AssociationRelationShip model) {
		this.polylineConnection.setDestinationName(model.getEndpointName());
		this.polylineConnection.setMultiplicity(model.getMultiplicity());
		this.polylineConnection.setContainment(model.isContainment());
	}

}
