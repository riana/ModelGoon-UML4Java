package org.modelgoon.classdiagram.editParts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.modelgoon.classdiagram.model.ClassDiagram;
import org.modelgoon.classdiagram.model.CommunicationClass;
import org.modelgoon.classdiagram.model.UmlClass;
import org.modelgoon.classes.editparts.ClassCompartmentModel;
import org.modelgoon.jdt.model.AssociationRelationShip;
import org.modelgoon.jdt.model.CommunicationRelationship;
import org.modelgoon.jdt.model.ExtensionRelationShip;
import org.modelgoon.jdt.model.Field;
import org.modelgoon.jdt.model.Method;

public class ClassDiagramEditPartFactory implements EditPartFactory {

	public EditPart createEditPart(final EditPart context, final Object model) {
		EditPart editPart = null;
		if (model instanceof ClassDiagram) {
			editPart = new ClassDiagramEditPart();
		} else if (model instanceof UMLClass) {
			editPart = new ClassEditPart();
		} else if (model instanceof CommunicationClass) {
			editPart = new CommunicationClassEditPart();
		} else if (model instanceof Field) {
			editPart = new FieldEditPart();
		} else if (model instanceof Method) {
			editPart = new MethodEditPart();
		} else if (model instanceof CommunicationRelationship) {
			editPart = new CommunicationEditPart();
		} else if (model instanceof ExtensionRelationShip) {
			editPart = new ExtensionEditPart();
		} else if (model instanceof AssociationRelationShip) {
			editPart = new AssociationEditPart();
		} else if (model instanceof ClassCompartmentModel) {
			editPart = new ListEditPart();
		}

		if (editPart != null) {
			editPart.setModel(model);
		}
		return editPart;
	}

}
