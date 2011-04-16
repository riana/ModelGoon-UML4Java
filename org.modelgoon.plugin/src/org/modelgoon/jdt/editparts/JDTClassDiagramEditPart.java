package org.modelgoon.jdt.editparts;

import java.util.ArrayList;
import java.util.List;

import org.modelgoon.core.ModelElement;
import org.modelgoon.core.ui.RootElementEditPart;
import org.modelgoon.jdt.model.UMLClass;
import org.modelgoon.jdt.model.UMLModel;

public class JDTClassDiagramEditPart extends RootElementEditPart<UMLModel> {

	List<ModelElement> children = new ArrayList<ModelElement>();

	public JDTClassDiagramEditPart() {
		super();
		addCreationCommand(UMLClass.class, new CreateClassCommand(this));
	}

	@Override
	protected List<?> getModelChildren() {
		this.children.clear();
		UMLModel model = getModelElement();
		this.children.addAll(model.getClasses());
		this.children.addAll(model.getNotes());
		return this.children;
	}
}
