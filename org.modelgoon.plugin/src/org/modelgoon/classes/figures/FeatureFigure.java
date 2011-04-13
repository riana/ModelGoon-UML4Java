package org.modelgoon.classes.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

public class FeatureFigure extends Figure {

	Label label;

	final Font staticFont;

	final Font normalFont;

	public FeatureFigure() {
		FontData nfd = new FontData("Arial", 8, SWT.ITALIC);
		this.normalFont = new Font(null, nfd);
		FontData fd = new FontData("Arial", 8, SWT.ITALIC);
		this.staticFont = new Font(null, fd);

		this.label = new Label();
		setLayoutManager(new FlowLayout(true));
		add(this.label);
	}

	public void setStatic(final boolean isStatic) {
		if (isStatic) {
			this.label.setFont(this.staticFont);
		} else {
			this.label.setFont(this.normalFont);
		}
	}

	public void setFeatureSummary(final String featureSummary) {
		this.label.setText(featureSummary);
	}

}
