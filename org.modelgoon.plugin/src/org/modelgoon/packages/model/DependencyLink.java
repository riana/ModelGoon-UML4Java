package org.modelgoon.packages.model;

public class DependencyLink {

	PackageElement source;

	PackageElement destination;

	public DependencyLink(final PackageElement source,
			final PackageElement destination) {
		super();
		this.source = source;
		this.destination = destination;
		source.getSourceLinks().add(this);
		destination.getDestinationLinks().add(this);
	}

	public void disconnect() {
		this.source.getSourceLinks().remove(this);
		this.destination.getDestinationLinks().remove(this);
	}

}
