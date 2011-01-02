package org.modelgoon.classdiagram.model;

public class AssociationRelationShip extends Relationship {

	public static final String END_POINT_NAME = "endpointName";

	public static final String MULTIPLICITY = "multiplicity";

	public static final String CONTAINMENT = "containment";

	String endpointName;

	String multiplicity;

	boolean containment;

	public String getEndpointName() {
		return this.endpointName;
	}

	public void setEndpointName(final String endpointName) {
		this.endpointName = endpointName;
		firePropertyChange(AssociationRelationShip.END_POINT_NAME);
	}

	public String getMultiplicity() {
		return this.multiplicity;
	}

	public void setMultiplicity(final String multiplicity) {
		this.multiplicity = multiplicity;
		firePropertyChange(AssociationRelationShip.MULTIPLICITY);
	}

	public boolean isContainment() {
		return this.containment;
	}

	public void setContainment(final boolean containment) {
		this.containment = containment;
		firePropertyChange(AssociationRelationShip.CONTAINMENT);
	}

}
