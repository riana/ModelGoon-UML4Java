package org.modelgoon.classdiagram.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.modelgoon.classes.figures.ClassFigure;
import org.modelgoon.classes.figures.LineMarginBorder;

public class CommunicationClassFigure extends Figure {

	Label label;

	public CommunicationClassFigure() {
		setLayoutManager(new FlowLayout(true));
		this.label = new Label();
		this.label.setTextAlignment(PositionConstants.CENTER);
		add(this.label);
		setBorder(new LineMarginBorder(ClassFigure.borderColor));
	}

	public void setName(final String name) {
		this.label.setText(": " + name);
	}

}
