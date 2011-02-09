package org.modelgoon.core;

import java.util.Observable;

public class RootModelElement extends Observable {

	public final void propertyChanged() {
		setChanged();
		notifyObservers();
	}

}
