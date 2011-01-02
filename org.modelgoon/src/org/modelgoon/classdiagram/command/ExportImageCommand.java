package org.modelgoon.classdiagram.command;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.FileDialog;
import org.modelgoon.classdiagram.editor.DiagramEditor;
import org.modelgoon.classdiagram.model.ClassDiagram;
import org.modelgoon.dao.DataAccessLayer;

public class ExportImageCommand extends WorkbenchPartAction {

	final DiagramEditor classDiagramEditor;

	DataAccessLayer dataAccessLayer;

	public static final String ID = "Export Image Command";

	public ExportImageCommand(final DiagramEditor classDiagramEditor,
			final DataAccessLayer dataAccessLayer) {
		super(classDiagramEditor);
		this.classDiagramEditor = classDiagramEditor;
		this.dataAccessLayer = dataAccessLayer;

	}

	@Override
	protected void init() {
		super.init();
		setId(ExportImageCommand.ID);
		setText("Save as Image");
	}

	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	@Override
	public void run() {
		ClassDiagram diagram = this.classDiagramEditor.getDiagram();
		FileDialog dialog = new FileDialog(this.classDiagramEditor.getSite()
				.getShell(), SWT.SAVE);
		dialog.setFilterNames(new String[] { "Image Files", "All Files (*.*)" });
		dialog.setFilterExtensions(new String[] { "*.png", "*.*" }); // Windows
																		// wild
																		// cards
																		// diagram.get
		// dialog.setFileName("classDiagram.png");
		dialog.setFilterPath(this.dataAccessLayer.getWorkingDirectory());
		String name = this.dataAccessLayer.getFileName();
		name = name.substring(0, name.lastIndexOf(".")) + ".png";
		dialog.setFileName(name);
		String fileName = dialog.open();

		IFigure figure = this.classDiagramEditor.getPrintableFigure();

		Device device = this.classDiagramEditor.getEditorSite()
				.getWorkbenchWindow().getShell().getDisplay();
		Rectangle r = figure.getBounds();

		Image image = null;
		GC gc = null;
		Graphics g = null;
		try {
			image = new Image(device, r.width, r.height);
			gc = new GC(image);
			g = new SWTGraphics(gc);
			g.translate(r.x * -1, r.y * -1);
			figure.paint(g);

			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { image.getImageData() };
			imageLoader.save(fileName, SWT.IMAGE_PNG);
		} finally {
			if (g != null) {
				g.dispose();
			}
			if (gc != null) {
				gc.dispose();
			}
			if (image != null) {
				image.dispose();
			}
		}

	}
}
