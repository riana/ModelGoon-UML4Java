package org.modelgoon.jdt.model;

import org.modelgoon.classes.model.AssociationKind;

public class AssociationRelationShip extends Relationship {

	String endpointName;

	String multiplicity;

	AssociationKind associationKind = AssociationKind.Simple;

	public String getEndpointName() {
		return this.endpointName;
	}

	public void setEndpointName(final String endpointName) {
		this.endpointName = endpointName;
		propertyChanged();
	}

	public String getMultiplicity() {
		return this.multiplicity;
	}

	public void setMultiplicity(final String multiplicity) {
		this.multiplicity = multiplicity;
		propertyChanged();
	}

	public AssociationKind getAssociationKind() {
		return this.associationKind;
	}

	public void setAssociationKind(final AssociationKind associationKind) {
		this.associationKind = associationKind;
		propertyChanged();
	}

}
