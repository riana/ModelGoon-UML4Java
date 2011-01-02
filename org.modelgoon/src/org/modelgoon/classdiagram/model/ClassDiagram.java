package org.modelgoon.classdiagram.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class ClassDiagram extends ModelElement implements
		IElementChangedListener {

	public final static String CLASSES = "classesRegistry";

	private final Map<String, ClassModel> classesRegistry = new HashMap<String, ClassModel>();

	private IJavaProject javaProject;

	public void addClass(final ClassModel umlClass) {
		if (!this.classesRegistry.containsKey(umlClass.getName())) {
			umlClass.setDiagram(this);
			this.classesRegistry.put(umlClass.getName(), umlClass);
			consolidateDiagram();
		}
	}

	public void removeClass(final ClassModel umlClass) {
		this.classesRegistry.remove(umlClass.getName());
		for (Relationship relationship : umlClass.getOutgoingRelationships()) {
			relationship.getDestination().removeIncomingRelationship(
					relationship);
		}
		for (Relationship relationship : umlClass.getIncomingRelationships()) {
			relationship.getSource().removeOutgoingRelationship(relationship);
		}
		consolidateDiagram();
		// firePropertyChange(ClassDiagram.CLASSES);
	}

	public final ClassModel resolveType(final String type) {
		return this.classesRegistry.get(type);
	}

	public List<ClassModel> getClasses() {
		return new ArrayList<ClassModel>(this.classesRegistry.values());
	}

	public ClassModel getUmlClass(final String qualifiedName) {
		return this.classesRegistry.get(qualifiedName);
	}

	public void consolidateDiagram() {
		for (ClassModel diagramClass : this.classesRegistry.values()) {
			diagramClass.consolidate();
		}
		firePropertyChange(ModelElement.NAME);
	}

	@Override
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
			for (ClassModel diagramClass : this.classesRegistry.values()) {
				String qualifiedName = diagramClass.getName();
				IType type = this.javaProject.findType(qualifiedName);
				diagramClass.setJavaElement(type);
			}
			consolidateDiagram();
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
