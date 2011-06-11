package org.modelgoon.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class RootModelElement extends Observable {

	List<Note> notes = new ArrayList<Note>();

	public final void propertyChanged() {
		setChanged();
		notifyObservers();
	}

	public void removeNote(final Note note) {
		this.notes.remove(note);
		propertyChanged();
	}

	public void addNote(final Note note) {
		this.notes.add(note);
		note.setParent(this);
		propertyChanged();
	}

	public List<Note> getNotes() {
		return this.notes;
	}

}
