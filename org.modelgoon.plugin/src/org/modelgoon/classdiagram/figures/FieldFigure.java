package org.modelgoon.classdiagram.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.modelgoon.classdiagram.model.Field;
import org.modelgoon.classdiagram.model.StructuralFeature;

public class FieldFigure extends Figure {

	Label label;

	public FieldFigure() {
		this.label = new Label();
		setLayoutManager(new FlowLayout(true));
		add(this.label);
	}

	public void setField(final StructuralFeature field) {
		this.label.setText(field.toString());
		if (field.isStatic()) {
			FontData fd = new FontData("Arial", 12, SWT.ITALIC);
			this.label.setFont(new Font(null, fd));
		}
	}

	public void setField(final Field field) {
		this.label.setText(field.toString());
		if (field.isStatic() || field.isLiteral()) {
			FontData fd = new FontData("Arial", 12, SWT.ITALIC);
			this.label.setFont(new Font(null, fd));
		}
	}

	public void setTextualDescription(final String textualDescription) {
		this.label.setText(textualDescription);
	}

}
