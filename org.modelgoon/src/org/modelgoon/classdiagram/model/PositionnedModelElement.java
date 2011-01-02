package org.modelgoon.classdiagram.model;

import org.eclipse.draw2d.geometry.Point;

public class PositionnedModelElement extends ModelElement {

	public static final String LOCATION = "location";

	private final Point location = new Point();

	public final void setLocation(final Point pt) {
		this.location.setLocation(pt);
		firePropertyChange(PositionnedModelElement.LOCATION);
	}

	public final void setLocation(final int x, final int y) {
		this.location.setLocation(x, y);
		firePropertyChange(PositionnedModelElement.LOCATION);
	}

	public final int getX() {
		return this.location.x;
	}

	public final void setX(final int x) {
		this.location.x = x;
	}

	public final int getY() {
		return this.location.y;
	}

	public final void setY(final int y) {
		this.location.y = y;
	}

	public final Point getLocation() {
		return this.location;
	}

}
