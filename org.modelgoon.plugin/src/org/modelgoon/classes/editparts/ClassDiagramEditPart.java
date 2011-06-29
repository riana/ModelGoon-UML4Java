package org.modelgoon.classes.editparts;

import java.util.ArrayList;
import java.util.List;

import org.modelgoon.classes.model.ClassDiagram;
import org.modelgoon.core.ModelElement;
import org.modelgoon.core.ui.RootElementEditPart;

public class ClassDiagramEditPart extends RootElementEditPart<ClassDiagram> {

	List<ModelElement> children = new ArrayList<ModelElement>();

	public ClassDiagramEditPart() {

	}

	@Override
	protected List<?> getModelChildren() {
		this.children.clear();
		ClassDiagram classDiagram = getModelElement();
		this.children.addAll(classDiagram.getClasses());
		this.children.addAll(classDiagram.getNotes());
		return this.children;
	}
}
