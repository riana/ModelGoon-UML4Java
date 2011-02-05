package org.modelgoon.classdiagram.command;

import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.modelgoon.classdiagram.editor.DiagramEditor;

public class FitDiagramToPageCommand extends WorkbenchPartAction {

	final DiagramEditor classDiagramEditor;

	ZoomManager zoomManager;

	public static final String ID = "Fit Diagram Command";

	public FitDiagramToPageCommand(final DiagramEditor classDiagramEditor,
			final ZoomManager zoomManager) {
		super(classDiagramEditor);
		this.classDiagramEditor = classDiagramEditor;
		this.zoomManager = zoomManager;

	}

	@Override
	protected void init() {
		super.init();
		setId(FitDiagramToPageCommand.ID);
		setText("Fit to page");
	}

	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	@Override
	public void run() {
		this.zoomManager.setZoomAsText(ZoomManager.FIT_ALL);
	}
}