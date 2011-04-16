package org.modelgoon.jdt.editparts;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jdt.ui.actions.OpenNewClassWizardAction;
import org.eclipse.jdt.ui.actions.OpenNewInterfaceWizardAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.modelgoon.core.ModelElement;
import org.modelgoon.core.ui.CreationCommand;
import org.modelgoon.jdt.model.UMLClass;
import org.modelgoon.jdt.model.UMLModel;

public class CreateClassCommand extends CreationCommand {

	JDTClassDiagramEditPart editPart;

	public CreateClassCommand(final JDTClassDiagramEditPart editPart) {
		super();
		this.editPart = editPart;
	}

	@Override
	public void attachNewElement(final ModelElement newObject) {
		UMLModel model = this.editPart.getModelElement();
		UMLClass umlClass = (UMLClass) newObject;
		IJavaElement createdElement = null;
		AbstractOpenWizardAction openNewClassWizardAction;
		if (!umlClass.isInterface()) {
			OpenNewClassWizardAction action = new OpenNewClassWizardAction();
			action.setOpenEditorOnFinish(false);
			openNewClassWizardAction = action;

		} else {
			OpenNewInterfaceWizardAction action = new OpenNewInterfaceWizardAction();
			action.setOpenEditorOnFinish(false);
			openNewClassWizardAction = action;
		}
		openNewClassWizardAction.setSelection(new StructuredSelection(model
				.getJavaProject()));

		openNewClassWizardAction.run();
		createdElement = openNewClassWizardAction.getCreatedElement();
		if ((createdElement != null) && (createdElement instanceof IType)) {
			IType newType = (IType) createdElement;

			umlClass.setJavaType(newType);
			this.editPart.getModelElement().addClass(umlClass);
		}

	}

}
