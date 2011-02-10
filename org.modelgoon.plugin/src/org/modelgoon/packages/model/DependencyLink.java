package org.modelgoon.packages.model;

import org.modelgoon.core.AbstractConnection;

public class DependencyLink extends AbstractConnection {

	PackageElement source;

	PackageElement destination;

	boolean cyclic = false;

	public DependencyLink(final PackageElement source,
			final PackageElement destination) {
		super();
		this.source = source;
		this.destination = destination;
		source.getSourceLinks().add(this);
		destination.addDestinationLink(this);
	}

	public boolean isCyclic() {
		return this.cyclic;
	}

	public void disconnect() {
		this.source.getSourceLinks().remove(this);
		this.destination.getDestinationLinks().remove(this);
	}

	public void consolidate() {
		this.cyclic = this.destination.dependsUpon(this.source);
		System.out.println("Cyclic : " + this.cyclic);
		propertyChanged();
	}

}
