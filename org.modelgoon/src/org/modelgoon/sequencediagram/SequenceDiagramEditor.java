package org.modelgoon.sequencediagram;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorInput;
import org.modelgoon.classdiagram.editor.DoNothingTool;
import org.modelgoon.sequencediagram.editparts.SequenceDiagramEditPartFactory;
import org.modelgoon.sequencediagram.model.InteractionModel;

public class SequenceDiagramEditor extends GraphicalEditorWithFlyoutPalette {

	public SequenceDiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setActiveTool(new DoNothingTool());
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return new PaletteRoot();
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();

		ScalableFreeformRootEditPart root = new ScalableFreeformRootEditPart();
		List zoomLevels = new ArrayList(3);
		zoomLevels.add(ZoomManager.FIT_ALL);
		zoomLevels.add(ZoomManager.FIT_WIDTH);
		zoomLevels.add(ZoomManager.FIT_HEIGHT);
		root.getZoomManager().setZoomLevelContributions(zoomLevels);

		IAction zoomIn = new ZoomInAction(root.getZoomManager());
		IAction zoomOut = new ZoomOutAction(root.getZoomManager());
		// IAction fit = new FitDiagramToPageCommand(this,
		// root.getZoomManager());
		getActionRegistry().registerAction(zoomIn);
		getActionRegistry().registerAction(zoomOut);
		// getActionRegistry().registerAction(fit);

		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(new SequenceDiagramEditPartFactory());

		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1),
				MouseWheelZoomHandler.SINGLETON);
	}

	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();

		GraphicalViewer graphicalViewer = getGraphicalViewer();
		graphicalViewer.setContents(this.interactionModelBuilder);
		// getCommandStack().addCommandStackListener(new CommandStackListener()
		// {
		//
		// @Override
		// public void commandStackChanged(final EventObject event) {
		// firePropertyChange(IEditorPart.PROP_DIRTY);
		// }
		// });
	}

	InteractionModel interactionModelBuilder;

	public void setModel(final InteractionModel interactionModelBuilder) {
		this.interactionModelBuilder = interactionModelBuilder;
		GraphicalViewer graphicalViewer = getGraphicalViewer();
		graphicalViewer.setContents(this.interactionModelBuilder);
	}

	@Override
	protected void setInput(final IEditorInput input) {
		super.setInput(input);
		String fileName = input.getName();
		setPartName(fileName.substring(0, fileName.lastIndexOf(".mgs")));
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

}
