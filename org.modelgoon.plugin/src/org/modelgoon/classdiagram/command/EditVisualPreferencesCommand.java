package org.modelgoon.classdiagram.command;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.window.Window;
import org.modelgoon.classdiagram.editParts.ClassEditPart;
import org.modelgoon.classdiagram.editParts.ClassModelEditPart;
import org.modelgoon.classdiagram.editor.DiagramEditor;
import org.modelgoon.classdiagram.model.UmlClass;
import org.modelgoon.classes.dialogs.ClassVisualPreferencesDialog;

public class EditVisualPreferencesCommand extends SelectionAction {

	DiagramEditor classDiagramEditor;

	public static final String ID = "Filter elements";

	public EditVisualPreferencesCommand(
			final DiagramEditor classDiagramEditor) {
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
				|| (!(getSelectedObjects().get(0) instanceof ClassEditPart))) {
			return false;
		}
		// and we want the model to be a Connection object.
		ClassModelEditPart cep = (ClassModelEditPart) getSelectedObjects().get(0);
		if (cep.getModel() instanceof UMLClass) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {

		ClassModelEditPart cep = (ClassModelEditPart) getSelectedObjects().get(0);
		UMLClass umlClass = (UMLClass) cep.getModel();
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
