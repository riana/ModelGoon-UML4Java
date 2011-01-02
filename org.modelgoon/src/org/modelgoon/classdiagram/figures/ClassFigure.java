package org.modelgoon.classdiagram.figures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

public class ClassFigure extends Figure {

	public static Color classColor = new Color(null, 255, 255, 206);

	public static Color borderColor = new Color(null, 100, 100, 206);

	private final Figure labelAndStereotypeFigure = new Figure();

	private final CompartmentFigure featuresFigure = new CompartmentFigure(4);

	Label label;

	Figure stereotypeFigure;

	Map<String, Label> stereotype = new HashMap<String, Label>();

	public ClassFigure() {
		this.label = new Label();
		this.label.setTextAlignment(PositionConstants.CENTER);

		ToolbarLayout layout = new ToolbarLayout();
		layout.setSpacing(4);
		setLayoutManager(layout);
		int hinsets = 5;
		int vinsets = 5;
		setBorder(new MarginBorder(vinsets, hinsets, vinsets, hinsets));
		setLayoutManager(layout);
		setBackgroundColor(ClassFigure.classColor);
		setOpaque(false);

		ToolbarLayout labelAndStereotypelayout = new ToolbarLayout();
		labelAndStereotypelayout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
		labelAndStereotypelayout.setStretchMinorAxis(false);
		labelAndStereotypelayout.setSpacing(2);
		this.labelAndStereotypeFigure
				.setLayoutManager(labelAndStereotypelayout);

		this.stereotypeFigure = new Figure();
		ToolbarLayout stereotypelayout = new ToolbarLayout(true);
		stereotypelayout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		stereotypelayout.setStretchMinorAxis(false);
		stereotypelayout.setSpacing(2);
		this.stereotypeFigure.setLayoutManager(stereotypelayout);

		this.labelAndStereotypeFigure.add(this.label);
		this.labelAndStereotypeFigure.add(this.stereotypeFigure);
		add(this.labelAndStereotypeFigure);
		add(this.featuresFigure);
	}

	public CompartmentFigure getFeaturesCompartment() {
		return this.featuresFigure;
	}

	@Override
	public void paintFigure(final Graphics graphics) {
		Insets insets = getInsets();
		Rectangle rect = new Rectangle(getBounds());
		rect.shrink(1, 1);
		// graphics.fillRoundRectangle(rect, 10, 10);
		// graphics.drawRoundRectangle(rect, 10, 10);
		Color initialColor = getForegroundColor();
		graphics.setForegroundColor(ClassFigure.borderColor);
		graphics.drawRectangle(rect);

		Rectangle headerBounds = this.labelAndStereotypeFigure.getBounds();
		int attributeSeparation = rect.y + headerBounds.height + insets.top + 2;
		graphics.drawLine(rect.x, attributeSeparation, rect.x + rect.width,
				attributeSeparation);

		List<Figure> children = this.featuresFigure.getChildren();
		if (!children.isEmpty()) {
			headerBounds = children.get(0).getBounds();
			int methodsSeparation = attributeSeparation + headerBounds.height
					+ 4;
			graphics.drawLine(rect.x, methodsSeparation, rect.x + rect.width,
					methodsSeparation);
			graphics.setForegroundColor(initialColor);
		}
		// System.out.println("ClassFigure.paintFigure() @ " + rect);

	}

	public void addStereotype(final String string) {
		Label stereotypeLabel = this.stereotype.get(string);
		if (stereotypeLabel == null) {
			stereotypeLabel = new Label("\u00AB" + string + "\u00BB");
			FontData fd = new FontData("Arial", 8, SWT.ITALIC);
			stereotypeLabel.setFont(new Font(null, fd));
			this.stereotypeFigure.add(stereotypeLabel);
			this.stereotype.put(string, stereotypeLabel);
		}
	}

	public void setClassName(final String name) {
		this.label.setText(name);
	}

	public void setAbstract(final boolean isAbstract) {
		if (isAbstract) {
			addStereotype("Asbtract");
		} else {
			removeStereotype("Abstract");
		}
	}

	private void removeStereotype(final String string) {
		Label stereotypeLabel = this.stereotype.get(string);
		if (stereotypeLabel != null) {
			this.stereotypeFigure.remove(stereotypeLabel);
		}

	}

	public void setInterface(final boolean isInterface) {
		if (isInterface) {
			addStereotype("Interface");
		} else {
			removeStereotype("Abstract");
		}

	}

	public void setSingleton(final boolean singleton) {
		if (singleton) {
			addStereotype("Singleton");
		} else {
			removeStereotype("Singleton");
		}
	}

	public void setEnum(final boolean isEnum) {
		if (isEnum) {
			addStereotype("Enum");
		} else {
			removeStereotype("Enum");
		}
	}

	public void setInternal(final boolean internal) {
		if (internal) {
			addStereotype("Internal");
		} else {
			removeStereotype("Internal");
		}
	}
}