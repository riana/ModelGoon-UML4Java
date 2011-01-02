package org.modelgoon.packages.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.modelgoon.ModelGoonActivator;

public class PackageAnalysis {

	IJavaProject javaProject;

	Map<String, Package> packages = new HashMap<String, Package>();

	boolean dirty = false;

	public PackageAnalysis() {
	}

	public void setJavaProject(final IJavaProject javaProject) {
		this.javaProject = javaProject;
		for (Package pkg : this.packages.values()) {
			pkg.setJavaProject(this);
		}
	}

	public void addPackage(final Package pkg) {
		this.packages.put(pkg.getName(), pkg);
	}

	public Package addPackage(final IResource resource) {
		Package newPackage = null;
		IProject project = resource.getProject();
		System.out.println("PackageAnalysis.addPackage("
				+ resource.getFullPath() + ") in project : " + project);
		try {
			IPackageFragment packageFragment = this.javaProject
					.findPackageFragment(resource.getFullPath());
			System.out.println("Package framgment : " + packageFragment);
			if ((packageFragment != null)
					&& !packageFragment.getElementName().isEmpty()) {
				Package pkg = this.packages.get(packageFragment
						.getElementName());
				if (pkg == null) {
					newPackage = new Package();
					newPackage.setPackageFragment(packageFragment);
					this.packages.put(newPackage.getName(), newPackage);
					this.dirty = true;
				}
				System.out.println("\tpackage : " + pkg);
			}
		} catch (CoreException e) {
			ModelGoonActivator.getDefault().log(e.getMessage(), e);
		}
		return newPackage;
	}

	private boolean isInClasspath(final IProject project)
			throws JavaModelException {
		String[] dependencies = this.javaProject.getRequiredProjectNames();
		for (String string : dependencies) {
			if (project.getName().equals(string)) {
				return true;
			}
		}
		return false;
	}

	public Package findPackage(final String name) {
		return this.packages.get(name);
	}

	public boolean isDirty() {
		return this.dirty;
	}

	public Collection<Package> getPackages() {
		return this.packages.values();
	}

	public void removePackage(final Package pkg) {
		this.packages.remove(pkg.getName());
		this.dirty = true;
	}

	public IPackageFragment findElement(final String packageName) {
		IPath path = Path
				.fromPortableString(packageName.replaceAll("[.]", "/"));
		try {

			IJavaElement element = this.javaProject.findElement(path);
			if ((element != null) && (element instanceof IPackageFragment)) {
				return (IPackageFragment) element;
			}

		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setDirty(final boolean b) {
		this.dirty = b;
	}

	public void update() {
		for (Package pkg : this.packages.values()) {
			pkg.update();
		}
	}

}
