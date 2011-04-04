package org.modelgoon.classes.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

public class FeatureFigure extends Figure {

	Label label;

	public FeatureFigure() {
		this.label = new Label();
		setLayoutManager(new FlowLayout(true));
		add(this.label);
	}

	public void setStatic(final boolean isStatic) {
		if (isStatic) {
			FontData fd = new FontData("Arial", 12, SWT.ITALIC);
			this.label.setFont(new Font(null, fd));
		}
	}

	public void setFeatureSummary(final String featureSummary) {
		this.label.setText(featureSummary);
	}

}
