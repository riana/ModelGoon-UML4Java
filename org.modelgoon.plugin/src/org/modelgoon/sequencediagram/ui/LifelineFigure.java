package org.modelgoon.sequencediagram.ui;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Color;
import org.modelgoon.classdiagram.figures.ClassFigure;

public class LifelineFigure extends Figure {

	String label = null;

	boolean actor = false;

	final static int TEXT_VERTICAL_INSETS = 10;

	final static int TEXT_HORIZONTAL_INSETS = 20;

	final static int BORDER_INSETS = 10;

	public LifelineFigure() {
		super();
	}

	public void setActor(final boolean actor) {
		this.actor = actor;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		Dimension labelSize = calculateTextSize();
		return new Dimension(labelSize.width + 50, 500);
	}

	protected Dimension calculateTextSize() {
		return TextUtilities.INSTANCE.getTextExtents(this.label, getFont());
	}

	@Override
	protected void paintFigure(final Graphics graphics) {
		super.paintFigure(graphics);

		Color initialColor = getForegroundColor();
		graphics.setForegroundColor(ClassFigure.borderColor);

		Dimension labelSize = calculateTextSize();
		int figureHeight = 40;
		graphics.translate(getLocation());
		graphics.setForegroundColor(ClassFigure.borderColor);
		int xCenter = (int) (getSize().width / 2.0);
		if (!this.actor) {
			Dimension labelBorderSize = labelSize.expand(
					LifelineFigure.TEXT_HORIZONTAL_INSETS,
					LifelineFigure.TEXT_VERTICAL_INSETS);
			figureHeight = labelBorderSize.height
					+ LifelineFigure.BORDER_INSETS;

			graphics.drawRectangle(
					(int) ((getSize().width - labelBorderSize.width) / 2.0),
					LifelineFigure.BORDER_INSETS, labelBorderSize.width,
					labelBorderSize.height);
		} else {
			final int circleRadius = 4;
			final int handsLength = 7;
			final int bodyStartY = LifelineFigure.BORDER_INSETS + circleRadius
					* 2;
			final int bodyEndY = bodyStartY + 10;
			final int handsY = bodyStartY + 4;
			final int footSpace = 5;

			graphics.drawOval(xCenter - circleRadius,
					LifelineFigure.BORDER_INSETS, circleRadius * 2,
					circleRadius * 2);

			graphics.drawLine(xCenter, bodyStartY, xCenter, bodyEndY);

			graphics.drawLine(xCenter - handsLength, handsY, xCenter
					+ handsLength, handsY);

			graphics.drawLine(xCenter, bodyEndY, xCenter - footSpace, bodyEndY
					+ handsLength);
			graphics.drawLine(xCenter, bodyEndY, xCenter + footSpace, bodyEndY
					+ handsLength);
		}

		graphics.setLineDash(new int[] { 5, 5 });
		graphics.drawLine(xCenter, figureHeight, xCenter, getSize().height);

		graphics.setForegroundColor(initialColor);
		if (!this.actor) {
			graphics.drawString(this.label,
					(int) ((getSize().width - labelSize.width) / 2.0)
							+ LifelineFigure.TEXT_HORIZONTAL_INSETS / 2,
					LifelineFigure.TEXT_VERTICAL_INSETS / 2
							+ LifelineFigure.BORDER_INSETS);
		}
	}

	int centerPosition = 0;

	@Override
	protected void layout() {
		super.layout();
		this.centerPosition = (int) (getLocation().x + (getSize().width / 2.0));
	}

	public int getCenterXPosition() {
		return this.centerPosition;
	}

}
