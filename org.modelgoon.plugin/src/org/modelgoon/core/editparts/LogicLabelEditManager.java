package org.modelgoon.core.editparts;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.CellEditorActionHandler;
import org.modelgoon.core.NoteFigure;

public class LogicLabelEditManager extends DirectEditManager {

	private IActionBars actionBars;
	private CellEditorActionHandler actionHandler;
	private IAction copy, cut, paste, undo, redo, find, selectAll, delete;
	private double cachedZoom = -1.0;
	private Font scaledFont;
	private final ZoomListener zoomListener = new ZoomListener() {
		public void zoomChanged(final double newZoom) {
			updateScaledFont(newZoom);
		}
	};

	public LogicLabelEditManager(final GraphicalEditPart source,
			final CellEditorLocator locator) {
		super(source, null, locator);
	}

	/**
	 * @see org.eclipse.gef.tools.DirectEditManager#bringDown()
	 */
	@Override
	protected void bringDown() {
		ZoomManager zoomMgr = (ZoomManager) getEditPart().getViewer()
				.getProperty(ZoomManager.class.toString());
		if (zoomMgr != null) {
			zoomMgr.removeZoomListener(this.zoomListener);
		}

		if (this.actionHandler != null) {
			this.actionHandler.dispose();
			this.actionHandler = null;
		}
		if (this.actionBars != null) {
			restoreSavedActions(this.actionBars);
			this.actionBars.updateActionBars();
			this.actionBars = null;
		}

		super.bringDown();
		// dispose any scaled fonts that might have been created
		disposeScaledFont();
	}

	@Override
	protected CellEditor createCellEditorOn(final Composite composite) {
		return new TextCellEditor(composite, SWT.MULTI | SWT.WRAP);
	}

	private void disposeScaledFont() {
		if (this.scaledFont != null) {
			this.scaledFont.dispose();
			this.scaledFont = null;
		}
	}

	@Override
	protected void initCellEditor() {
		// update text
		NoteFigure stickyNote = (NoteFigure) getEditPart().getFigure();
		getCellEditor().setValue(stickyNote.getNoteContents());
		// update font
		ZoomManager zoomMgr = (ZoomManager) getEditPart().getViewer()
				.getProperty(ZoomManager.class.toString());
		if (zoomMgr != null) {
			// this will force the font to be set
			this.cachedZoom = -1.0;
			updateScaledFont(zoomMgr.getZoom());
			zoomMgr.addZoomListener(this.zoomListener);
		} else {
			getCellEditor().getControl().setFont(stickyNote.getFont());
		}

		// Hook the cell editor's copy/paste actions to the actionBars so that
		// they can
		// be invoked via keyboard shortcuts.
		this.actionBars = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor().getEditorSite()
				.getActionBars();
		saveCurrentActions(this.actionBars);
		this.actionHandler = new CellEditorActionHandler(this.actionBars);
		this.actionHandler.addCellEditor(getCellEditor());
		this.actionBars.updateActionBars();
	}

	private void restoreSavedActions(final IActionBars actionBars) {
		actionBars
				.setGlobalActionHandler(ActionFactory.COPY.getId(), this.copy);
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
				this.paste);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
				this.delete);
		actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
				this.selectAll);
		actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), this.cut);
		actionBars
				.setGlobalActionHandler(ActionFactory.FIND.getId(), this.find);
		actionBars
				.setGlobalActionHandler(ActionFactory.UNDO.getId(), this.undo);
		actionBars
				.setGlobalActionHandler(ActionFactory.REDO.getId(), this.redo);
	}

	private void saveCurrentActions(final IActionBars actionBars) {
		this.copy = actionBars.getGlobalActionHandler(ActionFactory.COPY
				.getId());
		this.paste = actionBars.getGlobalActionHandler(ActionFactory.PASTE
				.getId());
		this.delete = actionBars.getGlobalActionHandler(ActionFactory.DELETE
				.getId());
		this.selectAll = actionBars
				.getGlobalActionHandler(ActionFactory.SELECT_ALL.getId());
		this.cut = actionBars.getGlobalActionHandler(ActionFactory.CUT.getId());
		this.find = actionBars.getGlobalActionHandler(ActionFactory.FIND
				.getId());
		this.undo = actionBars.getGlobalActionHandler(ActionFactory.UNDO
				.getId());
		this.redo = actionBars.getGlobalActionHandler(ActionFactory.REDO
				.getId());
	}

	private void updateScaledFont(final double zoom) {
		if (this.cachedZoom == zoom) {
			return;
		}

		Text text = (Text) getCellEditor().getControl();
		Font font = getEditPart().getFigure().getFont();

		disposeScaledFont();
		this.cachedZoom = zoom;
		if (zoom == 1.0) {
			text.setFont(font);
		} else {
			FontData fd = font.getFontData()[0];
			fd.setHeight((int) (fd.getHeight() * zoom));
			text.setFont(this.scaledFont = new Font(null, fd));
		}
	}

}