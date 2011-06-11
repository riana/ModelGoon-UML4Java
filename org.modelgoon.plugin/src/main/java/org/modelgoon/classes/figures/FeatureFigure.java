package org.modelgoon.classes.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.modelgoon.ModelGoonActivator;

public class FeatureFigure extends Figure {

	Label label;

	public FeatureFigure() {
		this.label = new Label();
		setLayoutManager(new FlowLayout(true));
		add(this.label);
	}

	public void setStatic(final boolean isStatic) {
		if (isStatic) {
			this.label
					.setFont(ModelGoonActivator.getDefaultFont(9, SWT.ITALIC));
		} else {
			this.label
					.setFont(ModelGoonActivator.getDefaultFont(9, SWT.NORMAL));
		}
	}

	public void setFeatureSummary(final String featureSummary) {
		this.label.setText(featureSummary);
	}

}
