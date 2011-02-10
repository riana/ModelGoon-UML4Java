package org.modelgoon.packages.model;

import java.util.ArrayList;
import java.util.Collection;
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

	public void addDestinationLink(final DependencyLink destinationLink) {
		this.destinationLinks.add(destinationLink);
		propertyChanged();
	}

	public void removeDestinationLink(final DependencyLink destinationLink) {
		this.destinationLinks.remove(destinationLink);
		propertyChanged();
	}

	public List<DependencyLink> getDestinationLinks() {
		return this.destinationLinks;
	}

	public void consolidate() throws JavaModelException {
		this.requiredPackages.clear();
		IPackageFragment packageFragment = this.packageDiagram
				.getPackageFragment(this);
		if (packageFragment != null) {
			Map<String, PackageUsage> deps = getDependencies(packageFragment);

			Iterator<Entry<String, DependencyLink>> iterator = this.links
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, DependencyLink> entry = iterator.next();
				if (!deps.containsKey(entry.getKey())) {
					iterator.remove();
					this.sourceLinks.remove(entry.getValue());
					entry.getValue().disconnectTarget();
				}
			}

			for (PackageUsage importedPackage : deps.values()) {
				String usedPackageName = importedPackage.getPackageName();
				PackageElement packageElement = this.packageDiagram
						.getPackage(usedPackageName);

				if (packageElement != null) {
					DependencyLink link = this.links.get(usedPackageName);
					if (link == null) {
						link = new DependencyLink(this, packageElement);
						this.links.put(usedPackageName, link);
					}
					link.setUsedClasses(importedPackage.getImportedClasses());
				}

			}
		}
		propertyChanged();
	}

	public Map<String, PackageUsage> getDependencies(
			final IPackageFragment packageFragment) throws JavaModelException {
		Map<String, PackageUsage> deps = new HashMap<String, PackageUsage>();
		for (IJavaElement javaElement : packageFragment.getChildren()) {
			if (javaElement instanceof IPackageFragment) {
				IPackageFragment pkg = (IPackageFragment) javaElement;
				if (pkg.hasChildren()) {
					Collection<PackageUsage> childrenDeps = getDependencies(pkg)
							.values();
					for (PackageUsage childImportedPackage : childrenDeps) {
						PackageUsage usage = deps.get(childImportedPackage
								.getPackageName());
						if (usage == null) {
							deps.put(usage.getPackageName(), usage);
						} else {
							usage.merge(childImportedPackage);
						}

					}
					// deps.addAll(getDependencies(pkg));
				}
			}
			if (javaElement instanceof ICompilationUnit) {
				ICompilationUnit cu = (ICompilationUnit) javaElement;
				for (IImportDeclaration imports : cu.getImports()) {
					String imp = imports.getElementName();
					String importedPackage = imp.substring(0,
							imp.lastIndexOf("."));
					if (!importedPackage.equals(getQualifiedName())) {
						String importedClass = imp.substring(imp
								.lastIndexOf(".") + 1);
						PackageUsage usage = deps.get(importedPackage);
						if (usage == null) {
							usage = new PackageUsage(importedPackage);
							deps.put(importedPackage, usage);
						}
						usage.addImportedClass(importedClass);
					}
				}
			}

		}
		return deps;
	}

	public boolean dependsUpon(final PackageElement source) {
		return this.links.containsKey(source.getQualifiedName());
	}

	public void removeFromDiagram() {
		for (DependencyLink link : this.destinationLinks) {
			link.disconnectSource();
		}
		this.destinationLinks.clear();
		for (DependencyLink link : this.sourceLinks) {
			link.disconnectTarget();
		}
		this.sourceLinks.clear();
		this.packageDiagram.removePackage(this);
	}

	public void removeSourceLink(final DependencyLink dependencyLink) {
		this.links.remove(dependencyLink.destination.getQualifiedName());
		this.sourceLinks.remove(dependencyLink);
		propertyChanged();
	}

	public void addSourceLink(final DependencyLink dependencyLink) {
		this.sourceLinks.add(dependencyLink);
		propertyChanged();
	}

}
