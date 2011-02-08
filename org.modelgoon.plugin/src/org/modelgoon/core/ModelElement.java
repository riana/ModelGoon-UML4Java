package org.modelgoon.core;

import java.util.Observable;

import org.eclipse.draw2d.geometry.Point;

public class ModelElement extends Observable {

	Point location = new Point(0, 0);

	public final void setLocation(final int x, final int y) {
		this.location.x = x;
		this.location.y = y;
		propertyChanged();
	}

	public final Point getLocation() {
		return this.location;
	}

	public void propertyChanged() {
		setChanged();
		notifyObservers();
	}
}
