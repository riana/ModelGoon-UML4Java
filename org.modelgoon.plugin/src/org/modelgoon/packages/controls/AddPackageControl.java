package org.modelgoon.packages.controls;

import org.eclipse.core.resources.IResource;
import org.modelgoon.packages.model.Package;
import org.modelgoon.packages.model.PackageAnalysis;


public class AddPackageControl {

	PackageAnalysis packageAnalysis;

	public AddPackageControl(final PackageAnalysis packageAnalysis) {
		super();
		this.packageAnalysis = packageAnalysis;
	}

	public void addPackage(final IResource resource, final int x, final int y) {
		Package pkg = this.packageAnalysis.addPackage(resource);
		if (pkg != null) {
			pkg.setLocation(x, y);
		}
	}

}
