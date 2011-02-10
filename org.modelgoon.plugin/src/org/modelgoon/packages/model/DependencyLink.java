package org.modelgoon.packages.model;

import java.util.Set;

import org.modelgoon.core.AbstractConnection;

public class DependencyLink extends AbstractConnection {

	PackageElement source;

	PackageElement destination;

	boolean cyclic = false;

	Set<String> importedClasses;

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

	public void setUsedClasses(final Set<String> importedClasses) {
		this.importedClasses = importedClasses;
		propertyChanged();
	}

	public Set<String> getImportedClasses() {
		return this.importedClasses;
	}

}
