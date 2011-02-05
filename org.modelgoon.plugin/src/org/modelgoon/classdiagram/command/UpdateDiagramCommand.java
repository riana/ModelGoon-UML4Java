package org.modelgoon.classdiagram.command;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.modelgoon.classdiagram.editor.DiagramEditor;
import org.modelgoon.classdiagram.model.ClassDiagram;

public class UpdateDiagramCommand extends WorkbenchPartAction {

	public static final String ID = "Update Diagram Command";

	DiagramEditor classDiagramEditor;

	public UpdateDiagramCommand(final DiagramEditor classDiagramEditor) {
		super(classDiagramEditor);
		this.classDiagramEditor = classDiagramEditor;
	}

	@Override
	protected void init() {
		super.init();
		setId(UpdateDiagramCommand.ID);
		setText("Update Diagram");
	}

	@Override
	public void run() {
		ClassDiagram classDiagram = this.classDiagramEditor.getDiagram();
		classDiagram.consolidateDiagram();
	}

	@Override
	protected boolean calculateEnabled() {
		return true;
	}

}
