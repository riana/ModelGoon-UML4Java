package org.modelgoon.sequencediagram.ui;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.modelgoon.classdiagram.figures.ClassFigure;

public class FrameFigure extends FragmentFigure {

	final static Color LABEL_BACKGROUND_COLOR = new Color(null, 240, 240, 240);

	String label = "Frame";

	String guard = "";

	final static int ANGLE = 7;

	final Font labelFont;

	public FrameFigure() {
		super();
		setBorderInsets(20);
		FontData fd = new FontData("Arial", 8, SWT.BOLD);
		this.labelFont = new Font(null, fd);
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	protected Dimension calculateTextSize() {
		return TextUtilities.INSTANCE
				.getTextExtents(this.label, this.labelFont);
	}

	@Override
	public void paint(final Graphics graphics) {
		super.paint(graphics);
		Color originalColor = graphics.getForegroundColor();
		graphics.setForegroundColor(ClassFigure.borderColor);

		Rectangle rect = new Rectangle(getBounds());
		rect = rect.crop(new Insets(1));

		Point labelPosition = new Point(getLocation().x + 4,
				getLocation().y + 4);
		Dimension labelSize = calculateTextSize();

		int minX = rect.x;
		int maxX = rect.x + labelSize.width + FrameFigure.ANGLE * 2;
		int minY = rect.y;
		int maxY = rect.y + labelSize.height + FrameFigure.ANGLE;

		PointList points = new PointList();
		points.addPoint(minX, minY);
		points.addPoint(minX, maxY);
		points.addPoint(maxX - FrameFigure.ANGLE, maxY);
		points.addPoint(maxX, maxY - FrameFigure.ANGLE);
		points.addPoint(maxX, minY);

		graphics.setLineWidth(2);
		graphics.setBackgroundColor(FrameFigure.LABEL_BACKGROUND_COLOR);
		graphics.fillPolygon(points);
		graphics.drawPolygon(points);

		graphics.drawRectangle(rect);

		graphics.setLineDash(new int[] { 15, 5 });
		for (Object child : getChildren()) {
			if (child instanceof CombinedFragmentFigure) {
				CombinedFragmentFigure fragment = (CombinedFragmentFigure) child;
				if (!fragment.isEmpty()) {
					Rectangle bounds = fragment.getBounds();
					graphics.setForegroundColor(ClassFigure.borderColor);
					graphics.drawLine(rect.x, bounds.y, rect.x + rect.width,
							bounds.y);
					graphics.setForegroundColor(originalColor);

				}
			}

		}

		graphics.setForegroundColor(originalColor);
		graphics.drawString(this.guard, maxX + 10, rect.y + 5);

		graphics.setFont(this.labelFont);
		graphics.drawString(this.label, labelPosition);

	}

	public void setGuard(final String guard) {
		this.guard = guard;
	}

	public String getGuard() {
		return this.guard;
	}

	@Override
	protected void layout() {

		Dimension labelSize = calculateTextSize();
		Dimension guardSize = TextUtilities.INSTANCE.getTextExtents(this.guard,
				getFont());
		int minimunWidth = labelSize.width + FrameFigure.ANGLE * 2 + 10
				+ guardSize.width + 10;
		setMinimumSize(new Dimension(minimunWidth, getMinimumSize().height));
		super.layout();
		System.out.println("FrameFigure.layout()");
	}
}
