package org.modelgoon.core;

public class Note extends ModelElement {

	String content = "Write your note here";

	RootModelElement parent;

	public Note() {
		super();
	}

	public void setParent(final RootModelElement parent) {
		this.parent = parent;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(final String content) {
		this.content = content;
		System.out.println("Note.setContent() " + content);
		propertyChanged();
	}

	public void removeFromDiagram() {
		this.parent.removeNote(this);
	}

}
