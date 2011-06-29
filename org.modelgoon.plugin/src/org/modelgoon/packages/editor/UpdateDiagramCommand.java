package org.modelgoon.packages.editor;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.modelgoon.packages.model.PackageDiagram;

public class UpdateDiagramCommand extends WorkbenchPartAction {

	public static final String ID = "Update Diagram Command";

	PackageDiagramEditor packageDiagramEditor;

	public UpdateDiagramCommand(final PackageDiagramEditor packageDiagramEditor) {
		super(packageDiagramEditor);
		this.packageDiagramEditor = packageDiagramEditor;
	}

	@Override
	protected void init() {
		super.init();
		setId(UpdateDiagramCommand.ID);
		setText("Update Diagram");
	}

	@Override
	public void run() {
		PackageDiagram packageDiagram = this.packageDiagramEditor.getModel();
		packageDiagram.consolidate();
	}

	@Override
	protected boolean calculateEnabled() {
		return true;
	}

}
