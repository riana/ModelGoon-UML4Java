package org.modelgoon.jdt.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.modelgoon.core.RootModelElement;

public class UMLModel extends RootModelElement {

	private final Map<String, UMLClass> classesRegistry = new HashMap<String, UMLClass>();

	private IJavaProject javaProject;

	public void addClass(final UMLClass umlClass) {
		if (!this.classesRegistry.containsKey(umlClass.getQualifiedName())) {
			umlClass.setDiagram(this);
			this.classesRegistry.put(umlClass.getQualifiedName(), umlClass);
			consolidateDiagram();
		}
	}

	public void removeClass(final UMLClass umlClass) {
		this.classesRegistry.remove(umlClass.getQualifiedName());
		consolidateDiagram();
		propertyChanged();
	}

	public final UMLClass resolveType(final String type) {
		return this.classesRegistry.get(type);
	}

	public List<UMLClass> getClasses() {
		return new ArrayList<UMLClass>(this.classesRegistry.values());
	}

	public UMLClass getUmlClass(final String qualifiedName) {
		return this.classesRegistry.get(qualifiedName);
	}

	public void consolidateDiagram() {
		for (UMLClass diagramClass : this.classesRegistry.values()) {
			diagramClass.consolidate();
		}
		propertyChanged();
	}

	public void elementChanged(final ElementChangedEvent event) {
		IJavaElementDelta delta = event.getDelta();
		if (delta != null) {
			System.out.println("delta received: ");
			System.out.print(delta);
		}
	}

	public final void setJavaProject(final IJavaProject javaProject) {
		this.javaProject = javaProject;
		try {
			for (UMLClass diagramClass : this.classesRegistry.values()) {
				String qualifiedName = diagramClass.getQualifiedName();
				IType type = this.javaProject.findType(qualifiedName);
				diagramClass.setJavaType(type);
			}
			consolidateDiagram();
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

}
