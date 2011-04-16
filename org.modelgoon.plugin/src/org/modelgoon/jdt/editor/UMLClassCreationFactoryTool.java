package org.modelgoon.jdt.editor;

import org.eclipse.gef.requests.CreationFactory;
import org.modelgoon.jdt.model.UMLClass;

public class UMLClassCreationFactoryTool implements CreationFactory {

	boolean isInterface = false;

	public UMLClassCreationFactoryTool(final boolean isInterface) {
		super();
		this.isInterface = isInterface;
	}

	public Object getNewObject() {
		UMLClass umlClass = new UMLClass();
		umlClass.setInterface(this.isInterface);
		return umlClass;
	}

	public Object getObjectType() {
		return UMLClass.class;
	}

}
