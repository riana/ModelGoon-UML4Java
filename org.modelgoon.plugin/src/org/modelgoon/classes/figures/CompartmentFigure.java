package org.modelgoon.classes.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.ToolbarLayout;

public class CompartmentFigure extends Figure {

	public CompartmentFigure(final int spacing) {
		ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		layout.setStretchMinorAxis(false);
		layout.setSpacing(spacing);
		setLayoutManager(layout);

	}

}