package org.modelgoon.classdiagram.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.swt.graphics.Color;

public class LineMarginBorder extends LineBorder {

	public LineMarginBorder(final Color color) {
		super(color);
	}

	@Override
	public Insets getInsets(final IFigure figure) {
		return new Insets(getWidth() + 10);
	}

}