package org.modelgoon.jdt.model;

public class AssociationRelationShip extends Relationship {

	String endpointName;

	String multiplicity;

	boolean containment;

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

	public boolean isContainment() {
		return this.containment;
	}

	public void setContainment(final boolean containment) {
		this.containment = containment;
		propertyChanged();
	}

}
