package org.modelgoon.packages.figures;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.draw2d.AbstractBackground;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.modelgoon.ModelGoonActivator;
import org.modelgoon.packages.model.Package;

public class PackageFigure extends Figure {

	// public static Color packageColor = new Color(null, 255, 0, 0);
	public static Color packageColor = new Color(null, 255, 255, 206);

	Label label;

	Package pkg;

	private transient Rectangle tempRect = new Rectangle();

	public PackageFigure() {
		FlowLayout layout = new FlowLayout();
		setLayoutManager(layout);
		this.label = new Label();
		add(this.label);

		this.label.setIcon(ModelGoonActivator.getImageDescriptor(
				"icons/package_obj.gif").createImage());

		setBackgroundColor(packageColor);
		setBorder(new RoundedBorder());
		setOpaque(true);

		setSize(-1, -1);

	}

	public PackageFigure(final Package pkg) {
		this();
		this.pkg = pkg;
		setPackage(pkg);
	}

	public void setPackage(final Package pkg) {
		this.pkg = pkg;
		this.label.setText(pkg.getName());
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
					NO_INSETS);
		}
	}
}
