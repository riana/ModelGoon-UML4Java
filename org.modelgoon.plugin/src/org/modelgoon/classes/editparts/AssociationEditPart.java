package org.modelgoon.classes.editparts;

import org.eclipse.draw2d.IFigure;
import org.modelgoon.classes.figures.AssociationConnection;
import org.modelgoon.classes.model.Association;
import org.modelgoon.core.ui.AbstractLinkEditPart;

public class AssociationEditPart extends AbstractLinkEditPart<Association> {

	AssociationConnection polylineConnection;

	@Override
	protected IFigure createFigure() {

		this.polylineConnection = new AssociationConnection();

		return this.polylineConnection;
	}

	@Override
	protected void doRefreshVisuals(final Association model) {
		this.polylineConnection.setDestinationName(model.getName());
		this.polylineConnection.setMultiplicity(model.getMultiplicity());
		this.polylineConnection.setContainment(model.isContainment());
	}

}
