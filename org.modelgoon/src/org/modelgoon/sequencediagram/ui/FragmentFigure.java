package org.modelgoon.sequencediagram.ui;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;

public class FragmentFigure extends Figure implements SequenceElement {

	int firstOffset = 25;

	int elementsOffset = 20;

	int yPosition = 0;

	int xPosition = 0;

	int borderInsets = 20;

	transient int verticalExtent;

	public FragmentFigure() {
		setLayoutManager(new XYLayout());
	}

	public void setBorderInsets(final int borderInsets) {
		this.borderInsets = borderInsets;
	}

	public int getBorderInsets() {
		return this.borderInsets;
	}

	public void setFirstOffset(final int firstOffset) {
		this.firstOffset = firstOffset;
	}

	public final void updateElementsLayout() {
		if (!getChildren().isEmpty()) {
			int minX = Integer.MAX_VALUE;
			int maxX = 0;
			int yPosition = this.firstOffset + this.elementsOffset;
			for (Object child : getChildren()) {
				if (child instanceof SequenceElement) {
					SequenceElement element = (SequenceElement) child;
					if (!element.isEmpty()) {
						element.setYPosition(yPosition + this.yPosition);
						yPosition += element.getVerticalExtent()
								+ this.elementsOffset;
						// Computing horizontal Extent
						if (minX > element.getMinX()) {
							minX = element.getMinX();
						}
						if (maxX < element.getMaxX()) {
							maxX = element.getMaxX();
						}
					}
				}

			}
			this.xPosition = minX - this.borderInsets;
			int width = maxX - minX + this.borderInsets * 2;
			this.verticalExtent = yPosition;
			setPreferredSize(width, this.verticalExtent);

		} else {
			setVisible(false);
			this.xPosition = 300;
			this.verticalExtent = 40;
			setPreferredSize(100, this.verticalExtent);
		}
	}

	@Override
	public final int getVerticalExtent() {
		return this.verticalExtent;
	}

	@Override
	public final void setYPosition(final int yPosition) {
		this.yPosition = yPosition;
		updateElementsLayout();
		setLocation(new Point(this.xPosition, this.yPosition));
		setSize(getPreferredSize());
	}

	@Override
	public final int getMinX() {
		return this.xPosition;
	}

	@Override
	public final int getMaxX() {
		return this.xPosition + getSize().width;
	}

	@Override
	protected void layout() {
		super.layout();
		updateElementsLayout();
	}

	@Override
	public boolean isEmpty() {
		return getChildren().isEmpty();
	}
}
