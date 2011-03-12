package org.modelgoon.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.eclipse.draw2d.Bendpoint;

public class AbstractConnection extends Observable {

	List<Bendpoint> bendpoints = new ArrayList<Bendpoint>();

	public void propertyChanged() {
		setChanged();
		notifyObservers();
	}

	public void addBendpoint(final Bendpoint bendpoint, final int index) {
		this.bendpoints.add(index, bendpoint);
		propertyChanged();
	}

	public Bendpoint getBendpoint(final int index) {
		return this.bendpoints.get(index);
	}

	public Bendpoint removeBendpoint(final int index) {
		Bendpoint b = this.bendpoints.remove(index);
		propertyChanged();
		return b;
	}

	public void removeBendpoint(final Bendpoint bendpoint) {
		this.bendpoints.remove(bendpoint);
		propertyChanged();
	}

	public List<Bendpoint> getBendpoints() {
		return this.bendpoints;
	}
}
