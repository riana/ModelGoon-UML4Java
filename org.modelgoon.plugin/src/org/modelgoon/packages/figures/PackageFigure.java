package org.modelgoon.packages.figures;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.draw2d.AbstractBackground;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.modelgoon.ModelGoonActivator;

public class PackageFigure extends Figure {

	final static Color DARK_BLUE = new Color(Display.getDefault(), 1, 70, 122);
	final static Color LIGHT_YELLOW = new Color(Display.getDefault(), 255, 255,
			206);
	// public static Color packageColor = new Color(null, 255, 0, 0);
	public static Color packageColor = new Color(null, 255, 255, 206);

	Label label;

	private transient Rectangle tempRect = new Rectangle();

	public PackageFigure() {
		FlowLayout layout = new FlowLayout();
		setLayoutManager(layout);
		this.label = new Label();
		add(this.label);
		ImageDescriptor imgDesc = ModelGoonActivator
				.getImageDescriptor("icons/package_obj.gif");
		this.label.setIcon(imgDesc.createImage());

		this.label.setForegroundColor(PackageFigure.DARK_BLUE);
		setBackgroundColor(PackageFigure.LIGHT_YELLOW);
		setBorder(new RoundedBorder());
		setOpaque(true);

		setSize(-1, -1);

	}

	public void setPackageName(final String packageName) {
		this.label.setText(packageName);
	}

	public static Image loadImage(final String name) {
		try {
			return new Image(null, new FileInputStream("icons/" + name));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void paintFigure(final Graphics graphics) {
		if (isOpaque()) {
			this.tempRect.setBounds(getBounds());
			this.tempRect.shrink(1, 1);
			graphics.fillRoundRectangle(this.tempRect, 5, 5);
		}
		if (getBorder() instanceof AbstractBackground) {

			((AbstractBackground) getBorder()).paintBackground(this, graphics,
					IFigure.NO_INSETS);
		}
	}
}
