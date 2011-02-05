package org.modelgoon.core.ui;

import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;

public class FitDiagramToPageCommand extends WorkbenchPartAction {

	final Diagram diagram;

	ZoomManager zoomManager;

	public static final String ID = "Fit Diagram Command";

	public FitDiagramToPageCommand(final Diagram classDiagramEditor,
			final ZoomManager zoomManager) {
		super(classDiagramEditor);
		this.diagram = classDiagramEditor;
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