package org.modelgoon.packages.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Point;
import org.modelgoon.ModelGoonActivator;

public class Package {

	PackageAnalysis javaProject;

	private String name = "";

	IPackageFragment packageFragment;

	Point location = new Point(0, 0);

	Map<String, Dependency> dependencies = new HashMap<String, Dependency>();

	public Package() {

	}

	public void setJavaProject(final PackageAnalysis javaProject) {
		this.javaProject = javaProject;
		setPackageFragment(javaProject.findElement(this.name));
	}

	public void setPackageFragment(final IPackageFragment packageFragment) {
		this.packageFragment = packageFragment;
		this.dependencies.clear();
		this.name = packageFragment.getElementName();
		try {
			// System.out.println("Package : " + this.name);
			for (IJavaElement javaElement : packageFragment.getChildren()) {
				if (javaElement instanceof IPackageFragment) {
					IPackageFragment pkg = (IPackageFragment) javaElement;
					if (pkg.hasChildren()) {

					}
				}
				if (javaElement instanceof ICompilationUnit) {
					ICompilationUnit cu = (ICompilationUnit) javaElement;
					// System.out.println("\tJava : " + cu.getElementName());
					for (IImportDeclaration imports : cu.getImports()) {
						String imp = imports.getElementName();
						imp = imp.substring(0, imp.lastIndexOf("."));
						if (!imp.equals(this.name)) {
							addDependency(imp);
						}
						// System.out.println("\t\timport : " + imp);
					}
				}

				if (javaElement instanceof IClassFile) {
					IClassFile classFile = (IClassFile) javaElement;
					// System.out.println("Class : " +
					// classFile.getElementName());
				}

			}
		} catch (JavaModelException e) {
			ModelGoonActivator.getDefault().log(e.getMessage(), e);
		}
	}

	public void update() {
		setPackageFragment(this.packageFragment);
	}

	private void addDependency(final String packageName) {
		Dependency dependency = this.dependencies.get(packageName);
		if (dependency == null) {
			dependency = new Dependency(packageName);
			this.dependencies.put(packageName, dependency);
		}
		dependency.incrementWeight();
	}

	public Collection<Dependency> getDependencies() {
		setPackageFragment(this.packageFragment);
		return this.dependencies.values();
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setX(final int x) {
		this.location.x = x;
	}

	public void setY(final int y) {
		this.location.y = y;
	}

	public int getX() {
		return this.location.x;
	}

	public int getY() {
		return this.location.y;
	}

	public void setLocation(final int x, final int y) {
		this.location.x = x;
		this.location.y = y;

	}

	public Point getLocation() {
		return this.location;
	}

	public boolean dependsUpon(final Package pkg) {
		return this.dependencies.containsKey(pkg.getName());
	}

}
