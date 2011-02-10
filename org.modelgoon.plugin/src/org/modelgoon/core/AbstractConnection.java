package org.modelgoon.core;

import java.util.Observable;

public class AbstractConnection extends Observable {

	public void propertyChanged() {
		setChanged();
		notifyObservers();
	}
}
