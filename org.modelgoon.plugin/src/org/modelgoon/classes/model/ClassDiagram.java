package org.modelgoon.classes.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.modelgoon.core.RootModelElement;

public class ClassDiagram extends RootModelElement {

	Map<String, ClassElement> classes = new HashMap<String, ClassElement>();

	public void addClass(final ClassElement classElement) {
		this.classes.put(classElement.getQualifiedName(), classElement);
		propertyChanged();
	}

	public Collection<ClassElement> getClasses() {
		return this.classes.values();
	}

}
