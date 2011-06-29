package org.modelgoon.jdt.editor;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.window.Window;
import org.modelgoon.classes.dialogs.ClassVisualPreferencesDialog;
import org.modelgoon.jdt.editparts.UMLClassEditPart;
import org.modelgoon.jdt.model.UMLClass;

public class EditVisualPreferencesCommand extends SelectionAction {

	JDTDiagramEditor classDiagramEditor;

	public static final String ID = "Filter elements";

	public EditVisualPreferencesCommand(
			final JDTDiagramEditor classDiagramEditor) {
		super(classDiagramEditor);
		this.classDiagramEditor = classDiagramEditor;
	}

	@Override
	protected void init() {
		super.init();
		setId(EditVisualPreferencesCommand.ID);
		setText(EditVisualPreferencesCommand.ID);
	}

	@Override
	protected boolean calculateEnabled() {
		// we only want enabled if is single selection
		if ((getSelectedObjects().size() != 1)
				|| (!(getSelectedObjects().get(0) instanceof UMLClassEditPart))) {
			return false;
		}
		UMLClassEditPart cep = (UMLClassEditPart) getSelectedObjects().get(0);
		if (cep.getModel() instanceof UMLClass) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {

		UMLClassEditPart cep = (UMLClassEditPart) getSelectedObjects().get(0);
		UMLClass umlClass = cep.getModelElement();
		ClassVisualPreferencesDialog dialog = new ClassVisualPreferencesDialog(
				umlClass);
		dialog.setBlockOnOpen(true);
		dialog.open();
		if (dialog.getReturnCode() == Window.OK) {
			umlClass.setAttributesDisplayFilter(dialog
					.getAttributeDisplayFilter());
			umlClass.setMethodDisplayFilter(dialog.getMethodDisplayFilter());
			getCommandStack().execute(new Command() {
			});
		}
	}

}
