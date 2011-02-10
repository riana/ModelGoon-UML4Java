package org.modelgoon.packages.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.core.RootModelElement;

public class PackageDiagram extends RootModelElement {

	List<PackageElement> packages = new ArrayList<PackageElement>();

	Map<String, PackageElement> packagesByName = new HashMap<String, PackageElement>();

	IJavaProject javaProject;

	public void setJavaProject(final IJavaProject javaProject) {
		this.javaProject = javaProject;
	}

	public void addPackageFromResource(final IResource resource,
			final Point location) {
		try {
			IPackageFragment packageFragment = this.javaProject
					.findPackageFragment(resource.getFullPath());
			System.out.println("Package framgment : " + packageFragment);
			if ((packageFragment != null)
					&& !packageFragment.getElementName().isEmpty()) {
				PackageElement pkg = this.packagesByName.get(packageFragment
						.getElementName());
				if (pkg == null) {
					PackageElement newPackage = new PackageElement();
					newPackage.setQualifiedName(packageFragment
							.getElementName());
					newPackage.setLocation(location.x, location.y);
					addPackage(newPackage);
				}
				System.out.println("\tpackage : " + pkg);
			}
		} catch (CoreException e) {
			ModelGoonActivator.getDefault().log(e.getMessage(), e);
		}
	}

	public void addPackage(final PackageElement pkg) {
		this.packages.add(pkg);
		pkg.setPackageDiagram(this);
		this.packagesByName.put(pkg.getQualifiedName(), pkg);
	}

	public List<PackageElement> getPackages() {
		return this.packages;
	}

	public void removePackage(final PackageElement pkg) {
		this.packages.remove(pkg);
	}

	public IPackageFragment getPackageFragment(
			final PackageElement packageElement) {
		if (this.javaProject != null) {
			IPath path = Path.fromPortableString(packageElement
					.getQualifiedName().replaceAll("[.]", "/"));
			try {

				IJavaElement element = this.javaProject.findElement(path);
				if ((element != null) && (element instanceof IPackageFragment)) {
					return (IPackageFragment) element;
				}

			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public PackageElement getPackage(final String usedPackageName) {
		return this.packagesByName.get(usedPackageName);
	}

	public void consolidate() {
		try {
			for (PackageElement pkg : this.packagesByName.values()) {
				pkg.consolidate();
			}
			for (PackageElement pkg : this.packagesByName.values()) {
				for (DependencyLink link : pkg.getSourceLinks()) {
					link.consolidate();
				}
			}
			propertyChanged();
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
