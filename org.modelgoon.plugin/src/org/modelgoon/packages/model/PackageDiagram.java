package org.modelgoon.packages.model;

import java.util.ArrayList;
import java.util.List;

public class PackageDiagram {

	List<PackageElement> packages = new ArrayList<PackageElement>();

	public void addPackage(final PackageElement pkg) {
		this.packages.add(pkg);
	}

	public List<PackageElement> getPackages() {
		return this.packages;
	}

}
