package org.modelgoon.jdt.model;

public class ExtensionRelationShip extends Relationship {

	public boolean isRealization() {
		return getDestination().isInterface();
	}

}
