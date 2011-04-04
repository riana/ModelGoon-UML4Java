package org.modelgoon.classdiagram.editParts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.modelgoon.classdiagram.model.UmlClass;
import org.modelgoon.classes.editparts.ClassCompartmentModel;
import org.modelgoon.classes.figures.ClassFigure;

public class ClassEditPart extends ClassModelEditPart {

	@Override
	protected IFigure createFigure() {
		ClassFigure classFigure = new ClassFigure();
		return classFigure;
	}

	@Override
	protected List getModelChildren() {
		List<ClassCompartmentModel> structuralFeatures = new ArrayList<ClassCompartmentModel>();
		structuralFeatures.add(new ClassCompartmentModel(
				((UMLClass) getModel()).getAttributes()));
		structuralFeatures.add(new ClassCompartmentModel(
				((UMLClass) getModel()).getMethods()));
		return structuralFeatures;
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		ClassFigure classFigure = (ClassFigure) getFigure();
		if (classFigure != null) {
			UMLClass umlClass = (UMLClass) getModel();
			classFigure.setClassName(umlClass.getName().substring(
					umlClass.getName().lastIndexOf(".") + 1));
			classFigure.setLocation(umlClass.getLocation());
			classFigure.setSize(classFigure.getPreferredSize());
			classFigure.setInterface(umlClass.isInterface());
			classFigure.setAbstract(umlClass.isAbstract());
			classFigure.setSingleton(umlClass.isSingleton());
			classFigure.setEnum(umlClass.isEnum());
			classFigure.setInternal(umlClass.isInternal());
		}
		refreshChildren();
		refreshParentLayout();
	}

}
