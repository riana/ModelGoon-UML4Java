package org.modelgoon.classdiagram.editor;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.ResourceTransfer;
import org.modelgoon.classdiagram.command.EditVisualPreferencesCommand;
import org.modelgoon.classdiagram.command.ExportImageCommand;
import org.modelgoon.classdiagram.command.FitDiagramToPageCommand;
import org.modelgoon.classdiagram.command.OpenInEditorCommand;
import org.modelgoon.classdiagram.command.ShowMethodSequenceCommand;
import org.modelgoon.classdiagram.command.UpdateDiagramCommand;
import org.modelgoon.classdiagram.editParts.ClassDiagramEditPartFactory;
import org.modelgoon.classdiagram.model.ClassDiagram;
import org.modelgoon.classdiagram.model.ClassModelFactory;
import org.modelgoon.dao.DAOException;
import org.modelgoon.dao.DataAccessLayer;

public abstract class DiagramEditor extends GraphicalEditorWithFlyoutPalette {

	ClassDiagram diagram;
	DataAccessLayer dataAccessLayer;
	ClassContextMenuProvider classContextMenuProvider;

	ClassModelFactory classModelFactory;

	public DiagramEditor(final ClassModelFactory classModelFactory) {
		super();
		this.classModelFactory = classModelFactory;
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setActiveTool(new DoNothingTool());
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return new PaletteRoot();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		try {
			this.dataAccessLayer.saveData(this.diagram);
			getCommandStack().markSaveLocation();

		} catch (DAOException e) {
			e.printStackTrace();
		}
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
		IAction fit = new FitDiagramToPageCommand(this, root.getZoomManager());
		getActionRegistry().registerAction(zoomIn);
		getActionRegistry().registerAction(zoomOut);
		getActionRegistry().registerAction(fit);

		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(new ClassDiagramEditPartFactory());

		this.classContextMenuProvider = new ClassContextMenuProvider(viewer,
				getActionRegistry());
		viewer.setContextMenu(this.classContextMenuProvider);
		getSite().registerContextMenu(this.classContextMenuProvider, viewer);

		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1),
				MouseWheelZoomHandler.SINGLETON);
	}

	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		GraphicalViewer graphicalViewer = getGraphicalViewer();
		graphicalViewer.setContents(this.diagram);
		getGraphicalViewer().addDropTargetListener(
				(TransferDropTargetListener) new ResourcesDropTargetListener(
						getGraphicalViewer(), ResourceTransfer.getInstance(),
						new ClassCreationFactory(this.classModelFactory)));
		getCommandStack().addCommandStackListener(new CommandStackListener() {

			@Override
			public void commandStackChanged(final EventObject event) {
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		});
	}

	@Override
	protected void createActions() {
		super.createActions();
		ActionRegistry registry = getActionRegistry();
		IAction action;

		action = new UpdateDiagramCommand(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ExportImageCommand(this, this.dataAccessLayer);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new EditVisualPreferencesCommand(this);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new OpenInEditorCommand(this);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ShowMethodSequenceCommand(this);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

	}

	@Override
	protected void setInput(final IEditorInput input) {
		super.setInput(input);
		setPartName(input.getName());
		this.dataAccessLayer = new DataAccessLayer(input);
		this.dataAccessLayer
				.addMapping("org/modelgoon/classdiagram/xml/ClassDiagram.cas");
		try {
			this.diagram = this.dataAccessLayer.loadData();
		} catch (DAOException e) {
			// e.printStackTrace();
			this.diagram = new ClassDiagram();
		}
		this.diagram.setJavaProject(this.dataAccessLayer.getJavaProject());
		// JavaCore.addElementChangedListener(this.diagram,
		// ElementChangedEvent.POST_RECONCILE);
	}

	public ClassDiagram getDiagram() {
		return this.diagram;
	}

	public IFigure getPrintableFigure() {
		ScrollingGraphicalViewer graphicalViewer = (ScrollingGraphicalViewer) getGraphicalViewer();
		LayerManager lm = (LayerManager) graphicalViewer.getEditPartRegistry()
				.get(LayerManager.ID);
		return lm.getLayer(LayerConstants.PRINTABLE_LAYERS);
	}

}