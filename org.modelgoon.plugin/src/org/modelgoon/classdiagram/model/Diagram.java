package org.modelgoon.classdiagram.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public abstract class Diagram extends ModelElement {

	IJavaProject javaProject;

	Map<String, JavaType> types = new HashMap<String, JavaType>();

	public final void setJavaProject(final IJavaProject javaProject) {
		this.javaProject = javaProject;
		updateJavaProject(javaProject);
	}

	public final IType findType(final String qualifiedName)
			throws JavaModelException {
		String processedName = qualifiedName.replace("$", ".");
		return this.javaProject.findType(processedName);
	}

	public final IPackageFragment findPackageFragment(final String packageName)
			throws JavaModelException {
		IPath path = Path
				.fromPortableString(packageName.replaceAll("[.]", "/"));
		IJavaElement element = this.javaProject.findElement(path);
		if ((element != null) && (element instanceof IPackageFragment)) {
			return (IPackageFragment) element;
		}
		return null;
	}

	public void addType(final JavaType type) {
		if (!this.types.containsKey(type.getName())) {
			type.setDiagram(this);
			this.types.put(type.getName(), type);
			// consolidate();
			firePropertyChange(ClassDiagram.CLASSES);
		}
	}

	public final List<JavaType> getTypes() {
		return new ArrayList<JavaType>(this.types.values());
	}

	public final void consolidate() {
		for (JavaType type : this.types.values()) {
			type.consolidate();
		}
	}

	public final JavaType resolveType(final String type) {
		return this.types.get(type);
	}

	protected abstract void updateJavaProject(final IJavaProject javaProject);
}
