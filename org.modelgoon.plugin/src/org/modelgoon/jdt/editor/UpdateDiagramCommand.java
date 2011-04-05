package org.modelgoon.jdt.editor;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.modelgoon.jdt.model.UMLModel;

public class UpdateDiagramCommand extends WorkbenchPartAction {

	public static final String ID = "Update Diagram Command";

	JDTClassDiagramEditor classDiagramEditor;

	public UpdateDiagramCommand(final JDTClassDiagramEditor classDiagramEditor) {
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
		UMLModel classDiagram = this.classDiagramEditor.getModel();
		classDiagram.consolidateDiagram();
	}

	@Override
	protected boolean calculateEnabled() {
		return true;
	}

}
