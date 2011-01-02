package org.modelgoon.classdiagram.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.modelgoon.classdiagram.figures.AssociationConnection;
import org.modelgoon.classdiagram.model.AssociationRelationShip;

public class AssociationEditPart extends AbstractConnectionEditPart implements
		PropertyChangeListener {

	@Override
	protected IFigure createFigure() {
		AssociationConnection polylineConnection = new AssociationConnection();
		return polylineConnection;
	}

	@Override
	protected void refreshVisuals() {
		AssociationConnection polylineConnection = (AssociationConnection) getFigure();
		AssociationRelationShip model = (AssociationRelationShip) getModel();
		polylineConnection.setDestinationName(model.getEndpointName());
		polylineConnection.setMultiplicity(model.getMultiplicity());
		polylineConnection.setContainment(model.isContainment());
	}

	@Override
	public void propertyChange(final PropertyChangeEvent arg0) {
		refreshVisuals();
	}

	// @Override
	// public void activate() {
	// super.activate();
	// Relationship relationship = (Relationship) getModel();
	// relationship.getSource().addPropertyChangeListener(this);
	// relationship.getDestination().addPropertyChangeListener(this);
	// }
	//
	// @Override
	// public void deactivate() {
	// Relationship relationship = (Relationship) getModel();
	// relationship.getSource().removePropertyChangeListener(this);
	// relationship.getDestination().removePropertyChangeListener(this);
	// super.deactivate();
	// }

	@Override
	protected void createEditPolicies() {

	}

}
