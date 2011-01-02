package org.modelgoon.classdiagram.editor;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.modelgoon.classdiagram.model.ClassDiagram;

public class AutoRefreshPartListener implements IPartListener {

	DiagramEditor classDiagramEditor;

	public AutoRefreshPartListener(final DiagramEditor classDiagramEditor) {
		super();
		this.classDiagramEditor = classDiagramEditor;
	}

	@Override
	public void partActivated(final IWorkbenchPart part) {
		ClassDiagram classDiagram = this.classDiagramEditor.getDiagram();
		classDiagram.consolidateDiagram();
	}

	@Override
	public void partBroughtToTop(final IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partClosed(final IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partDeactivated(final IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partOpened(final IWorkbenchPart part) {
		// TODO Auto-generated method stub

	}

}
