package org.modelgoon.classdiagram.model;

public class ExtensionRelationShip extends Relationship {

	public boolean isRealization() {
		return getDestination().isInterface();
	}

}
