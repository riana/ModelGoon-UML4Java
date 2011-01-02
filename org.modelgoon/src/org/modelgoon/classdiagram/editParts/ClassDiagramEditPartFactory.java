package org.modelgoon.classdiagram.editParts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.modelgoon.classdiagram.model.AssociationRelationShip;
import org.modelgoon.classdiagram.model.ClassDiagram;
import org.modelgoon.classdiagram.model.CommunicationClass;
import org.modelgoon.classdiagram.model.CommunicationRelationship;
import org.modelgoon.classdiagram.model.ExtensionRelationShip;
import org.modelgoon.classdiagram.model.Field;
import org.modelgoon.classdiagram.model.Method;
import org.modelgoon.classdiagram.model.StructuralFeatureContainer;
import org.modelgoon.classdiagram.model.UmlClass;

public class ClassDiagramEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(final EditPart context, final Object model) {
		EditPart editPart = null;
		if (model instanceof ClassDiagram) {
			editPart = new ClassDiagramEditPart();
		} else if (model instanceof UmlClass) {
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
		} else if (model instanceof StructuralFeatureContainer) {
			editPart = new ListEditPart();
		}

		if (editPart != null) {
			editPart.setModel(model);
		}
		return editPart;
	}

}
