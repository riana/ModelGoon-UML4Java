package org.modelgoon.packages.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.modelgoon.core.RootModelElement;

public class PackageDiagram extends RootModelElement {

	List<PackageElement> packages = new ArrayList<PackageElement>();

	Map<String, PackageElement> packagesByName = new HashMap<String, PackageElement>();

	IJavaProject javaProject;

	public void setJavaProject(final IJavaProject javaProject) {
		this.javaProject = javaProject;
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
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
