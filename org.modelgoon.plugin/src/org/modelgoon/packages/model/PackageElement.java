package org.modelgoon.packages.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.modelgoon.core.ModelElement;

public class PackageElement extends ModelElement {

	PackageDiagram packageDiagram;

	String qualifiedName;

	List<DependencyLink> sourceLinks = new ArrayList<DependencyLink>();

	List<DependencyLink> destinationLinks = new ArrayList<DependencyLink>();

	List<String> requiredPackages = new ArrayList<String>();

	Map<String, DependencyLink> links = new HashMap<String, DependencyLink>();

	public void setPackageDiagram(final PackageDiagram packageDiagram) {
		this.packageDiagram = packageDiagram;
	}

	public String getQualifiedName() {
		return this.qualifiedName;
	}

	public void setQualifiedName(final String qualifiedName) {
		this.qualifiedName = qualifiedName;
		propertyChanged();
	}

	public List<DependencyLink> getSourceLinks() {
		return this.sourceLinks;
	}

	public List<DependencyLink> getDestinationLinks() {
		return this.destinationLinks;
	}

	public void consolidate() throws JavaModelException {
		this.requiredPackages.clear();
		IPackageFragment packageFragment = this.packageDiagram
				.getPackageFragment(this);
		if (packageFragment != null) {
			List<String> deps = getDependencies(packageFragment);
			for (String usedPackageName : deps) {
				PackageElement packageElement = this.packageDiagram
						.getPackage(usedPackageName);
				Iterator<Entry<String, DependencyLink>> iterator = this.links
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<String, DependencyLink> entry = iterator.next();
					if (!deps.contains(entry.getKey())) {
						iterator.remove();
						entry.getValue().disconnect();
					}
				}

				if ((packageElement != null)
						&& !this.links.containsKey(usedPackageName)) {
					DependencyLink link = new DependencyLink(this,
							packageElement);
					this.links.put(usedPackageName, link);
				}
			}

		}
	}

	public List<String> getDependencies(final IPackageFragment packageFragment)
			throws JavaModelException {
		List<String> deps = new ArrayList<String>();
		for (IJavaElement javaElement : packageFragment.getChildren()) {
			if (javaElement instanceof IPackageFragment) {
				IPackageFragment pkg = (IPackageFragment) javaElement;
				if (pkg.hasChildren()) {
					deps.addAll(getDependencies(pkg));
				}
			}
			if (javaElement instanceof ICompilationUnit) {
				ICompilationUnit cu = (ICompilationUnit) javaElement;
				for (IImportDeclaration imports : cu.getImports()) {
					String imp = imports.getElementName();
					imp = imp.substring(0, imp.lastIndexOf("."));
					if (!imp.equals(getQualifiedName())) {
						deps.add(imp);
					}
				}
			}

		}
		return deps;
	}

}
