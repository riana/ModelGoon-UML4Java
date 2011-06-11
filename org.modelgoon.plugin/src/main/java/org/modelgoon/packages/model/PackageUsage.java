package org.modelgoon.packages.model;

import java.util.HashSet;
import java.util.Set;

public class PackageUsage {

	String packageName;

	Set<String> importedClasses = new HashSet<String>();

	public PackageUsage(final String packageName) {
		super();
		this.packageName = packageName;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void addImportedClass(final String importedClass) {
		this.importedClasses.add(importedClass);
	}

	public Set<String> getImportedClasses() {
		return this.importedClasses;
	}

	public void merge(final PackageUsage childImportedPackage) {
		this.importedClasses.addAll(childImportedPackage.importedClasses);
	}

}
