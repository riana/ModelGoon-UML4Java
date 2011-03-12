package org.modelgoon.packages.model;

import java.util.HashSet;
import java.util.Set;

import org.modelgoon.core.AbstractConnection;

public class DependencyLink extends AbstractConnection {

	PackageElement source;

	PackageElement destination;

	boolean cyclic = false;

	Set<String> importedClasses = new HashSet<String>();

	public DependencyLink() {
	}

	public DependencyLink(final PackageElement source,
			final PackageElement destination) {
		super();
		this.source = source;
		this.destination = destination;
		source.addSourceLink(this);
		destination.addDestinationLink(this);
	}

	public boolean isCyclic() {
		return this.cyclic;
	}

	public void disconnectSource() {
		this.source.removeSourceLink(this);
		propertyChanged();
	}

	public void disconnectTarget() {
		this.destination.removeDestinationLink(this);
		propertyChanged();
	}

	public void consolidate() {
		this.cyclic = this.destination.dependsUpon(this.source);
		propertyChanged();
	}

	public void setUsedClasses(final Set<String> importedClasses) {
		this.importedClasses = importedClasses;
		propertyChanged();
	}

	public Set<String> getImportedClasses() {
		return this.importedClasses;
	}

	public PackageElement getDestination() {
		return this.destination;
	}

	public void setDestination(final PackageElement destination) {
		this.destination = destination;
	}

	public void connect() {
		if (!this.source.getSourceLinks().contains(this)) {
			this.source.addSourceLink(this);
		}
		if (!this.destination.getDestinationLinks().contains(this)) {
			this.destination.addDestinationLink(this);
		}
	}

}
