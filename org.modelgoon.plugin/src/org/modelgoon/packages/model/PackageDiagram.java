package org.modelgoon.packages.model;

import java.util.ArrayList;
import java.util.List;

public class PackageDiagram {

	List<PackageElement> packages = new ArrayList<PackageElement>();

	List<DependencyLink> dependencies = new ArrayList<DependencyLink>();

	public void addPackage(final PackageElement pkg) {
		this.packages.add(pkg);
	}

	public List<PackageElement> getPackages() {
		return this.packages;
	}

	public void addDependency(final DependencyLink dependency) {
		this.dependencies.add(dependency);
	}

	public List<DependencyLink> getDependencies() {
		return this.dependencies;
	}

}
