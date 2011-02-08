package org.modelgoon.packages.model;

import java.util.ArrayList;
import java.util.List;

import org.modelgoon.core.ModelElement;

public class PackageElement extends ModelElement {

	String qualifiedName;

	List<DependencyLink> sourceLinks = new ArrayList<DependencyLink>();

	List<DependencyLink> destinationLinks = new ArrayList<DependencyLink>();

	public String getQualifiedName() {
		return this.qualifiedName;
	}

	public void setQualifiedName(final String qualifiedName) {
		this.qualifiedName = qualifiedName;
		propertyChanged();
	}

	public List<DependencyLink> getSourceLinks() {
		return this.sourceLinks;
	}

	public List<DependencyLink> getDestinationLinks() {
		return this.destinationLinks;
	}

}
