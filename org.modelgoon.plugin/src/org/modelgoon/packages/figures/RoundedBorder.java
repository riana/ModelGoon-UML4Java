package org.modelgoon.packages.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;

public class RoundedBorder extends AbstractBorder {

	public Insets getInsets(final IFigure figure) {
		return new Insets(3, 5, 3, 10);
	}

	public void paint(final IFigure figure, final Graphics graphics,
			final Insets insets) {
		AbstractBorder.tempRect.setBounds(AbstractBorder.getPaintRectangle(
				figure, insets));
		AbstractBorder.tempRect.shrink(1, 1);
		graphics.setForegroundColor(ColorConstants.lightGray);
		// graphics.fillRoundRectangle(tempRect, 5, 5);
		graphics.drawRoundRectangle(AbstractBorder.tempRect, 5, 5);
	}

}
