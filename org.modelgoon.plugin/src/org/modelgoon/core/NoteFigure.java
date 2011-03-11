package org.modelgoon.core;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class NoteFigure extends Figure {

	final static Color DARK_BLUE = new Color(Display.getDefault(), 1, 70, 122);

	Label label;

	private transient Rectangle tempRect = new Rectangle();

	public NoteFigure() {
		FlowLayout layout = new FlowLayout();
		setLayoutManager(layout);
		this.label = new Label();
		add(this.label);

		this.label.setForegroundColor(NoteFigure.DARK_BLUE);

		setSize(-1, -1);

		setBorder(new MarginBorder(12, 10, 10, 10));

	}

	public void setContent(final String content) {
		this.label.setText(content);
		setSize(getPreferredSize());
	}

	@Override
	protected void paintFigure(final Graphics graphics) {
		this.tempRect.setBounds(getBounds());
		this.tempRect.shrink(1, 1);
		graphics.setForegroundColor(ColorConstants.lightGray);
		// graphics.fillRoundRectangle(tempRect, 5, 5);
		int noteBorderSize = 10;
		// graphics.drawRoundRectangle(tempRect, 5, 5);
		PointList noteContainer = new PointList();
		noteContainer.addPoint(this.tempRect.x + this.tempRect.width
				- noteBorderSize, this.tempRect.y);
		noteContainer.addPoint(this.tempRect.x, this.tempRect.y);
		noteContainer.addPoint(this.tempRect.x, this.tempRect.y
				+ this.tempRect.height);
		noteContainer.addPoint(this.tempRect.x + this.tempRect.width,
				this.tempRect.y + this.tempRect.height);

		noteContainer.addPoint(this.tempRect.x + this.tempRect.width,
				this.tempRect.y + noteBorderSize);

		graphics.setBackgroundColor(ColorConstants.yellow);
		graphics.fillPolygon(noteContainer);
		graphics.drawPolygon(noteContainer);

		graphics.drawLine(this.tempRect.x + this.tempRect.width
				- noteBorderSize, this.tempRect.y, this.tempRect.x
				+ this.tempRect.width - noteBorderSize, this.tempRect.y
				+ noteBorderSize);

		graphics.drawLine(this.tempRect.x + this.tempRect.width
				- noteBorderSize, this.tempRect.y + noteBorderSize,
				this.tempRect.x + this.tempRect.width, this.tempRect.y
						+ noteBorderSize);
	}

	public String getNoteContents() {
		return this.label.getText();
	}

}
