package org.modelgoon.core.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
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
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ResourceTransfer;
import org.modelgoon.packages.editor.ModelElementFactory;

public abstract class Diagram extends GraphicalEditorWithFlyoutPalette {

	private String filePath = null;

	DiagramContextMenuProvider contextMenuProvider;

	IPersistenceEventHandler persistenceEventHandler;

	ObjectCreationFactory creationFactory = new ObjectCreationFactory();

	ScalableFreeformRootEditPart root;

	ClassEditPartFactory classEditPartFactory = new ClassEditPartFactory();

	Object model;

	public Diagram(final Object model) {
		super();
		this.model = model;
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setActiveTool(new DoNothingTool());
	}

	public void setPersistenceEventHandler(
			final IPersistenceEventHandler persistenceEventHandler) {
		this.persistenceEventHandler = persistenceEventHandler;
	}

	public void setModelElementFactory(
			final ModelElementFactory modelElementFactory) {
		this.creationFactory.setModelElementFactory(modelElementFactory);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return new PaletteRoot();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if (this.persistenceEventHandler != null) {
			this.persistenceEventHandler.save(monitor);
			getCommandStack().markSaveLocation();
		}

	}

	@Override
	protected void configureGraphicalViewer() {
		System.out.println("Diagram.configureGraphicalViewer()");
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		this.contextMenuProvider = new DiagramContextMenuProvider(viewer,
				getActionRegistry());

		this.root = new ScalableFreeformRootEditPart();
		viewer.setRootEditPart(this.root);
		viewer.setEditPartFactory(this.classEditPartFactory);
		viewer.setContextMenu(this.contextMenuProvider);
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1),
				MouseWheelZoomHandler.SINGLETON);

		getSite().registerContextMenu(this.contextMenuProvider, viewer);

		List<String> zoomLevels = new ArrayList<String>(3);
		zoomLevels.add(ZoomManager.FIT_ALL);
		zoomLevels.add(ZoomManager.FIT_WIDTH);
		zoomLevels.add(ZoomManager.FIT_HEIGHT);
		this.root.getZoomManager().setZoomLevelContributions(zoomLevels);

		IAction zoomIn = new ZoomInAction(this.root.getZoomManager());
		IAction zoomOut = new ZoomOutAction(this.root.getZoomManager());
		IAction fit = new FitDiagramToPageCommand(this,
				this.root.getZoomManager());

		addAction(zoomIn, GEFActionConstants.GROUP_VIEW);
		addAction(zoomOut, GEFActionConstants.GROUP_VIEW);
		addAction(fit, GEFActionConstants.GROUP_VIEW);

		addAction(new ExportImageCommand(this), GEFActionConstants.GROUP_SAVE);
		registerActions();
	}

	public void registerEditPart(final Class<?> modelClass,
			final Class<? extends EditPart> editPartClass) {
		this.classEditPartFactory.registerEditPart(modelClass, editPartClass);
	}

	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		System.out.println("Diagram.initializeGraphicalViewer()");
		// getGraphicalViewer().addDropTargetListener(
		// (TransferDropTargetListener) new ResourcesDropTargetListener(
		// getGraphicalViewer(), ResourceTransfer.getInstance(),
		// this.creationFactory));
		getGraphicalViewer().setContents(this.model);
		getGraphicalViewer().addDropTargetListener(
				(TransferDropTargetListener) new ResourcesDropTargetListener(
						getGraphicalViewer(), ResourceTransfer.getInstance(),
						this.creationFactory));

		getCommandStack().addCommandStackListener(new CommandStackListener() {

			public void commandStackChanged(final EventObject event) {
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		});
	}

	public final void addSelectionAction(final IAction action,
			final String group) {
		getSelectionActions().add(action.getId());
		addAction(action, group);
	}

	public final void addAction(final IAction action, final String group) {
		this.contextMenuProvider.addAction(action, group);
		ActionRegistry registry = getActionRegistry();
		registry.registerAction(action);
	}

	@Override
	protected final void createActions() {
		super.createActions();
		;
	}

	@Override
	protected void setInput(final IEditorInput input) {
		super.setInput(input);

		setPartName(input.getName());
		if (this.persistenceEventHandler != null) {
			FileEditorInput fileEditorInput = (FileEditorInput) input;
			this.filePath = fileEditorInput.getPath().toOSString();
			this.model = this.persistenceEventHandler.load(this.filePath);
		}
	}

	public IFigure getPrintableFigure() {
		ScrollingGraphicalViewer graphicalViewer = (ScrollingGraphicalViewer) getGraphicalViewer();
		LayerManager lm = (LayerManager) graphicalViewer.getEditPartRegistry()
				.get(LayerManager.ID);
		return lm.getLayer(LayerConstants.PRINTABLE_LAYERS);
	}

	public final String getWorkingDirectory() {
		File currentFile = new File(this.filePath);
		return currentFile.getParent();
	}

	public final String getCurrentFileName() {
		return this.filePath;
	}

	protected abstract void registerActions();

}