package org.modelgoon.packages.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;

public class RoundedBorder extends AbstractBorder {

	@Override
	public Insets getInsets(IFigure figure) {
		return new Insets(10, 10, 10, 10);
	}

	@Override
	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		tempRect.setBounds(getPaintRectangle(figure, insets));
		tempRect.shrink(1, 1);
		// graphics.setForegroundColor(PackageFigure.packageColor);
		// graphics.fillRoundRectangle(tempRect, 5, 5);
		graphics.drawRoundRectangle(tempRect, 5, 5);
	}

}
