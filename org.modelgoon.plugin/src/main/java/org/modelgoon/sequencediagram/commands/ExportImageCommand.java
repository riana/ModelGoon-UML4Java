package org.modelgoon.sequencediagram.commands;

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
import org.modelgoon.sequencediagram.SequenceDiagramEditor;

public class ExportImageCommand extends WorkbenchPartAction {

	final SequenceDiagramEditor sequenceDiagramEditor;

	public static final String ID = "Export Image Command";

	public ExportImageCommand(final SequenceDiagramEditor sequenceDiagramEditor) {
		super(sequenceDiagramEditor);
		this.sequenceDiagramEditor = sequenceDiagramEditor;

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
		FileDialog dialog = new FileDialog(this.sequenceDiagramEditor.getSite()
				.getShell(), SWT.SAVE);
		dialog.setFilterNames(new String[] { "Image Files", "All Files (*.*)" });
		dialog.setFilterExtensions(new String[] { "*.png", "*.*" }); // Windows
																		// wild
																		// cards
																		// diagram.get
		// dialog.setFileName("classDiagram.png");
		System.out.println("ExportImageCommand.run() : "
				+ this.sequenceDiagramEditor.getWorkingDirectory());
		dialog.setFilterPath(this.sequenceDiagramEditor.getWorkingDirectory());
		String name = this.sequenceDiagramEditor.getPartName();
		name = name + ".png";
		dialog.setFileName(name);
		String fileName = dialog.open();

		if (fileName != null) {
			IFigure figure = this.sequenceDiagramEditor.getPrintableFigure();

			Device device = this.sequenceDiagramEditor.getEditorSite()
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
}
